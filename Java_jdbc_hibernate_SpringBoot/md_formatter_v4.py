#!/usr/bin/env python3
"""
md_formatter_v4.py — Intelligent Markdown Transformation Engine (v4)
=====================================================================
9-Pass pipeline that transforms interview-prep .md files into
professional, GitHub-ready documentation.

Passes:
  0. Strip existing fences (idempotency)
  1. Fix title banner (===== line 1 → # H1)
  2. Fix broken/mismatched code fence languages
  3. Auto-fence unclosed Java / YAML / properties / bash / XML blocks
  4. Fence ASCII flow diagrams (box-drawing chars, arrow art)
  5. Convert plain-text tables → GFM tables
  6. Fix Q: / A: patterns → proper headings + bold
  7. Wrap YAML / properties config blocks
  8. Final cleanup (blank lines, trailing spaces, EOF newline)

Usage:
  python md_formatter_v4.py <folder>        # process all .md files recursively
  python md_formatter_v4.py <file.md>       # process single file
"""

import os
import re
import sys
from pathlib import Path

# ---------------------------------------------------------------------------
# Language detection helpers
# ---------------------------------------------------------------------------

JAVA_PATTERNS = [
    r'^\s*(public|private|protected)\s+(static\s+)?(final\s+)?[\w<>\[\]]+\s+\w+\s*[\({]',
    r'^\s*@(Override|Bean|Component|Service|Repository|Controller|RestController|Autowired|Value|SpringBootApplication|Configuration|Transactional|Test|Before|After|Around|Aspect|Entity|Table|Column|NotNull|Valid)',
    r'^\s*(import\s+java|import\s+org\.spring|import\s+org\.hibernate|import\s+jakarta)',
    r'^\s*(class|interface|enum)\s+\w+',
    r'^\s*System\.out\.print',
    r'^\s*List<|Map<|Set<|Optional<|Stream<|CompletableFuture<',
    r'^\s*@(GetMapping|PostMapping|PutMapping|DeleteMapping|RequestMapping|PathVariable|RequestBody|RequestParam)',
    r'^\s*(new\s+\w+|return\s+new\s+\w+)',
    r'^\s*(try\s*\{|catch\s*\(|finally\s*\{|throw\s+new)',
    r'^\s*\w+\.\w+\(.*\)\s*;',
]

YAML_PATTERNS = [
    r'^(spring|server|management|app|logging|datasource|security|kafka|eureka|ribbon|feign|resilience4j|employee|item|course):\s*$',
    r'^\s{2,}(url|port|username|password|driver-class-name|active|include|name|host|address|group-id|bootstrap-servers):\s+',
    r'^\s+-\s+\w',
    r'^---\s*$',
]

PROPERTIES_PATTERNS = [
    r'^(server\.|spring\.|management\.|app\.|logging\.|datasource\.|security\.)\w+\s*=',
    r'^[a-z][a-z0-9.]+=[^\s]',
]

BASH_PATTERNS = [
    r'^\$\s+\S',
    r'^(sudo|apt|yum|brew|mvn|gradle|kubectl|docker|git|npm|pip)\s+',
    r'^#!/(bin/bash|usr/bin/env bash)',
    r'^(echo|export|source|chmod|chown|mkdir|cp|mv|rm|ls|cat|grep|curl|wget)\s+',
]

XML_PATTERNS = [
    r'^\s*<[?!/]?\w[\w:]*[\s>]',
    r'^\s*</\w',
    r'^\s*<\w+\s+\w+\s*=\s*"',
]

SQL_PATTERNS = [
    r'^\s*(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|TRUNCATE|GRANT|REVOKE|BEGIN|COMMIT|ROLLBACK)\s+',
    r'^\s*(FROM|WHERE|JOIN|LEFT JOIN|RIGHT JOIN|INNER JOIN|GROUP BY|ORDER BY|HAVING|LIMIT)\s+',
]

DIAGRAM_CHARS = set('┌┐└┘├┤┬┴┼─│╔╗╚╝╠╣╦╩╬═║')
ARROW_PATTERN = re.compile(r'[→←↑↓↔⇌⇒⇐⇔➜➔▶►◄▷]|--?>|<--')
BOX_LINE_PATTERN = re.compile(r'[+|]\s*[-=]{3,}|[-=]{3,}\s*[+|]')


def guess_lang(lines):
    """Guess the programming language of a code block from its lines."""
    text = '\n'.join(lines)
    for pat in SQL_PATTERNS:
        if re.search(pat, text, re.IGNORECASE | re.MULTILINE):
            # Make sure it's not actually Java with SQL strings
            if not any(re.search(p, text, re.MULTILINE) for p in JAVA_PATTERNS[:3]):
                return 'sql'
    for pat in XML_PATTERNS:
        if re.search(pat, text, re.MULTILINE):
            if not any(re.search(p, text, re.MULTILINE) for p in JAVA_PATTERNS[:3]):
                return 'xml'
    for pat in BASH_PATTERNS:
        if re.search(pat, text, re.MULTILINE):
            return 'bash'
    for pat in YAML_PATTERNS:
        if re.search(pat, text, re.MULTILINE):
            return 'yaml'
    for pat in PROPERTIES_PATTERNS:
        if re.search(pat, text, re.MULTILINE):
            return 'properties'
    for pat in JAVA_PATTERNS:
        if re.search(pat, text, re.MULTILINE):
            return 'java'
    return 'text'


def is_diagram_line(line):
    """Return True if line looks like part of an ASCII diagram."""
    stripped = line.strip()
    if not stripped:
        return False
    # Unicode box-drawing characters
    if any(c in DIAGRAM_CHARS for c in stripped):
        return True
    # Arrow characters
    if ARROW_PATTERN.search(stripped):
        return True
    # ASCII box lines (+---+ style)
    if BOX_LINE_PATTERN.search(stripped):
        return True
    return False


def is_flow_block(lines, idx):
    """Check if a run of lines starting at idx looks like an ASCII flow/diagram."""
    count = 0
    for i in range(idx, min(idx + 8, len(lines))):
        if is_diagram_line(lines[i]):
            count += 1
    return count >= 2


# ---------------------------------------------------------------------------
# Pass 0 – Strip existing fences (idempotency)
# ---------------------------------------------------------------------------

def pass0_strip_fences(lines):
    """Remove all existing triple-backtick fences to start clean."""
    result = []
    inside_fence = False
    for line in lines:
        stripped = line.rstrip()
        if re.match(r'^```', stripped):
            inside_fence = not inside_fence
            # Don't include fence lines – we'll re-add them in later passes
            continue
        result.append(line)
    return result


# ---------------------------------------------------------------------------
# Pass 1 – Fix title banner
# ---------------------------------------------------------------------------

def pass1_title_banner(lines):
    """
    Convert === banner blocks at the top of files into # H1 headings.
    Handles 3 patterns:
      Pattern A:  ===\nTITLE\n===          (banner + title + closing banner)
      Pattern B:  ===\nTITLE\nFor: ...     (banner + title, no closing banner)
      Pattern C:  ===\n  TITLE LINE1\n  TITLE LINE2\n=== (indented multi-line)
    Also removes any remaining standalone === lines deeper in the file.
    """
    result = []
    i = 0
    n = len(lines)

    # ---- Process the very first banner block (top of file) ----
    # Strip BOM from first line if present
    if lines and lines[0].startswith('\ufeff'):
        lines[0] = lines[0][1:]

    # Skip leading blank lines
    while i < n and not lines[i].strip():
        result.append(lines[i])
        i += 1

    if i < n and re.match(r'^[=]{8,}\s*$', lines[i].rstrip()):
        # Opening banner found — collect title lines until closing banner or heading
        i += 1  # skip the opening ===
        title_lines = []
        closing_found = False
        while i < n:
            l = lines[i].rstrip()
            if re.match(r'^[=]{8,}\s*$', l):
                closing_found = True
                i += 1  # skip closing ===
                break
            # Stop collecting title lines once we hit a clearly non-title line
            stripped = lines[i].strip()
            if stripped and re.match(r'^(##|####|\d+\.\s|Coverage|Source:|Missing|Concept)', stripped):
                break
            # Stop if line contains | (table row) or starts with # (heading)
            if stripped and ('|' in stripped or stripped.startswith('#')):
                break
            if stripped:
                title_lines.append(stripped)
            i += 1

        # Build a single title from collected lines
        # First line is always the document title; subsequent lines ("For:", "Target:") go to subtitle
        if title_lines:
            doc_title = title_lines[0]
            # Remove trailing | separators and extra whitespace
            doc_title = re.sub(r'\s*\|\s*$', '', doc_title).strip()
            result.append(f'# {doc_title}\n')
            # Add remaining title lines (For:, Target:, Source:) as italic subtitle
            if len(title_lines) > 1:
                subtitle_parts = [t for t in title_lines[1:] if t and not re.match(r'^={4,}', t)]
                if subtitle_parts:
                    # Use blockquote lines (not · separator which confuses table parser)
                    result.append(f'> *{subtitle_parts[0]}*\n')
                    for sp in subtitle_parts[1:]:
                        result.append(f'> *{sp}*\n')
            result.append('\n')

    # ---- Process remaining lines ----
    while i < n:
        line = lines[i]
        stripped = line.rstrip()
        # Remove any remaining standalone === lines (section dividers, not headings)
        if re.match(r'^[=]{8,}\s*$', stripped):
            result.append('\n')
        else:
            result.append(line)
        i += 1

    return result


# ---------------------------------------------------------------------------
# Pass 2 – Fix broken/mismatched code fence languages
# ---------------------------------------------------------------------------

FENCE_OPEN = re.compile(r'^```(\w*)$')

def pass2_fix_fence_languages(lines):
    """Re-detect language for each fenced block and correct mislabeled fences."""
    result = []
    i = 0
    while i < len(lines):
        line = lines[i].rstrip()
        m = FENCE_OPEN.match(line)
        if m:
            lang = m.group(1)
            # Collect block content
            block = []
            j = i + 1
            while j < len(lines):
                bl = lines[j].rstrip()
                if re.match(r'^```\s*$', bl):
                    break
                block.append(lines[j])
                j += 1
            # Re-detect language
            detected = guess_lang(block)
            # Only correct if current lang is wrong/empty/text
            if lang in ('', 'text', 'sql', 'typescript', 'bash'):
                better = guess_lang(block)
                if better not in ('text',):
                    lang = better
                elif lang == '':
                    lang = better
            # Special correction: Java blocks mislabeled as sql
            if lang == 'sql' and any(re.search(p, '\n'.join(l.rstrip() for l in block), re.MULTILINE) for p in JAVA_PATTERNS):
                lang = 'java'
            result.append(f'```{lang}\n')
            result.extend(block)
            if j < len(lines):
                result.append('```\n')
                i = j + 1
            else:
                i = j
        else:
            result.append(lines[i])
            i += 1
    return result


# ---------------------------------------------------------------------------
# Pass 3 – Auto-fence unclosed code blocks
# ---------------------------------------------------------------------------

def pass3_autofence(lines):
    """Detect unclosed code regions and wrap them in appropriate fences."""
    result = []
    i = 0

    # Patterns that strongly indicate we're entering a code block
    JAVA_START = re.compile(
        r'^\s*(public\s|private\s|protected\s|@Override|@Bean|@Component|@Service|@Repository|'
        r'@RestController|@Controller|@Configuration|@Transactional|@Entity|@Aspect|@Named|@Inject|'
        r'import\s+java|import\s+org\.spring|class\s+\w+\s*\{|interface\s+\w+)'
    )
    YAML_START = re.compile(
        r'^(spring|server|management|app|logging|datasource|security|kafka|eureka|employee|item):\s*$'
    )
    PROPS_START = re.compile(
        r'^(server\.|spring\.|management\.|logging\.|app\.)\w+\s*=\s*\S'
    )
    BASH_START = re.compile(r'^\$\s+\S|^(sudo|docker|kubectl|mvn|gradle|git)\s+')

    def looks_code(line):
        s = line.rstrip()
        for pat in [JAVA_START, YAML_START, PROPS_START, BASH_START]:
            if pat.search(s):
                return True
        return False

    def collect_code_block(lines, start):
        """Collect consecutive code-looking lines."""
        block = []
        i = start
        empty_streak = 0
        while i < len(lines):
            l = lines[i]
            stripped = l.strip()
            if not stripped:
                empty_streak += 1
                if empty_streak > 1:
                    break
                block.append(l)
            else:
                empty_streak = 0
                # Stop if we hit a heading or clearly prose
                if re.match(r'^#{1,6}\s', stripped):
                    break
                if re.match(r'^(Answer|ANSWER|Note|NOTE|When to use|Use case|Real|Flow|Internal|Code|Example):', stripped):
                    break
                block.append(l)
            i += 1
        # Trim trailing blank lines
        while block and not block[-1].strip():
            block.pop()
        return block, i

    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        # Skip existing fenced blocks (shouldn't be any after pass2, but safe)
        if re.match(r'^```', stripped):
            result.append(line)
            i += 1
            continue

        # Check for YAML block
        if YAML_START.match(stripped):
            block, end = collect_code_block(lines, i)
            if len(block) >= 2:
                result.append('```yaml\n')
                result.extend(block)
                result.append('```\n')
                i = end
                continue

        # Check for properties block
        if PROPS_START.match(stripped):
            block, end = collect_code_block(lines, i)
            if len(block) >= 2:
                result.append('```properties\n')
                result.extend(block)
                result.append('```\n')
                i = end
                continue

        result.append(line)
        i += 1

    return result


# ---------------------------------------------------------------------------
# Pass 4 – Fence ASCII diagram blocks
# ---------------------------------------------------------------------------

def pass4_fence_diagrams(lines):
    """Detect ASCII art / flow diagrams and wrap in ```text fences."""
    result = []
    i = 0

    while i < len(lines):
        line = lines[i]
        # Already inside a fence? (shouldn't happen after passes, but safe)
        if re.match(r'^```', line.rstrip()):
            result.append(line)
            i += 1
            continue

        if is_diagram_line(line):
            # Collect contiguous diagram lines (allow 1 blank line in middle)
            block = [line]
            j = i + 1
            blanks = 0
            while j < len(lines):
                nl = lines[j]
                if not nl.strip():
                    blanks += 1
                    if blanks > 1:
                        break
                    block.append(nl)
                elif is_diagram_line(nl):
                    blanks = 0
                    block.append(nl)
                else:
                    break
                j += 1
            # Only fence if block has 2+ diagram lines
            diagram_count = sum(1 for l in block if is_diagram_line(l))
            if diagram_count >= 2:
                # Trim trailing blank lines inside block
                while block and not block[-1].strip():
                    block.pop()
                result.append('```text\n')
                result.extend(block)
                result.append('\n```\n')
                i = j
                continue

        result.append(line)
        i += 1

    return result


# ---------------------------------------------------------------------------
# Pass 5 – Convert plain-text tables to GFM tables
# ---------------------------------------------------------------------------

def pass5_gfm_tables(lines):
    """
    Detect plain-text table-like structures and convert to GFM tables.
    Handles:
      - Lines with 2+ | separators (pipe tables without proper header separator)
      - Tab/space aligned columns with --- separator
    """
    result = []
    i = 0

    def is_pipe_row(line):
        s = line.strip()
        return s.count('|') >= 2

    def is_sep_row(line):
        s = line.strip()
        # Matches: |---|---| or |:---|---:| or plain ---|---
        return bool(re.match(r'^[\|:\-\s]+$', s)) and '-' in s and len(s) > 4

    def is_ascii_box_row(line):
        s = line.strip()
        return bool(re.match(r'^[+|][-=+|]+[+|]$', s))

    def parse_pipe_cells(line):
        s = line.strip().strip('|')
        return [c.strip() for c in s.split('|')]

    def parse_box_content_row(line):
        # Like: │ Feature │ Value │
        s = line.strip().strip('│|')
        parts = re.split(r'[│|]', s)
        return [p.strip() for p in parts if p.strip() or True]

    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        # Skip fenced content
        if re.match(r'^```', stripped):
            result.append(line)
            i += 1
            continue

        # Detect pipe table start
        if is_pipe_row(stripped) and not is_sep_row(stripped):
            # Look ahead: is next line a separator?
            j = i + 1
            while j < len(lines) and not lines[j].strip():
                j += 1

            next_line = lines[j].strip() if j < len(lines) else ''

            if is_sep_row(next_line):
                # Already a proper GFM table (header + separator)
                # Just normalize it
                table_rows = [line]
                # Add separator
                table_rows.append(lines[j])
                k = j + 1
                while k < len(lines) and is_pipe_row(lines[k].strip()):
                    table_rows.append(lines[k])
                    k += 1
                # Normalize each row
                cells_list = [parse_pipe_cells(r) for r in table_rows]
                max_cols = max(len(c) for c in cells_list)
                # Pad all rows
                for cells in cells_list:
                    while len(cells) < max_cols:
                        cells.append('')
                # Output header
                result.append('| ' + ' | '.join(cells_list[0]) + ' |\n')
                # Output separator
                result.append('|' + '|'.join(' --- ' for _ in range(max_cols)) + '|\n')
                # Output data rows
                for cells in cells_list[2:]:
                    result.append('| ' + ' | '.join(cells) + ' |\n')
                i = k
                continue
            else:
                # Pipe row without following separator — might be first row of table
                # Collect all consecutive pipe rows
                table_lines = []
                k = i
                while k < len(lines) and (is_pipe_row(lines[k].strip()) or is_sep_row(lines[k].strip())):
                    table_lines.append(lines[k])
                    k += 1
                if len(table_lines) >= 2:
                    # Find or create separator
                    has_sep = any(is_sep_row(l.strip()) for l in table_lines)
                    cells_list = []
                    sep_idx = -1
                    for ti, tl in enumerate(table_lines):
                        if is_sep_row(tl.strip()):
                            sep_idx = ti
                        else:
                            cells_list.append(parse_pipe_cells(tl))
                    max_cols = max((len(c) for c in cells_list), default=1)
                    for cells in cells_list:
                        while len(cells) < max_cols:
                            cells.append('')
                    # First row = header
                    result.append('| ' + ' | '.join(cells_list[0]) + ' |\n')
                    result.append('|' + '|'.join(' --- ' for _ in range(max_cols)) + '|\n')
                    for cells in cells_list[1:]:
                        result.append('| ' + ' | '.join(cells) + ' |\n')
                    i = k
                    continue

        result.append(line)
        i += 1

    return result


# ---------------------------------------------------------------------------
# Pass 6 – Fix Q: / A: patterns
# ---------------------------------------------------------------------------

def pass6_qa_headings(lines):
    """
    Convert:
      Q: What is X?   →   #### Q: What is X?
      A: It is ...    →   **A:** It is ...
      Q (Scenario): ...  →  #### Q (Scenario): ...
    """
    result = []
    Q_PAT = re.compile(r'^Q\s*[\(:]')
    A_PAT = re.compile(r'^A\s*:')

    for line in lines:
        stripped = line.strip()
        if Q_PAT.match(stripped) and not stripped.startswith('#'):
            # Check it's not already a heading
            result.append(f'#### {stripped}\n')
        elif A_PAT.match(stripped):
            # "A: text" → "**A:** text"
            rest = stripped[2:].lstrip(': ')
            result.append(f'**A:** {rest}\n')
        else:
            result.append(line)
    return result


# ---------------------------------------------------------------------------
# Pass 7 – Wrap YAML / properties config blocks
# ---------------------------------------------------------------------------

def pass7_wrap_configs(lines):
    """
    Find multi-line YAML or properties blocks that slipped through pass 3
    (e.g. indented YAML in middle of prose) and wrap them.
    """
    # This pass is intentionally light — pass 3 handles most cases.
    # Here we catch the remaining indented YAML blocks (2+ spaces, key: value).
    result = []
    i = 0

    INDENTED_YAML = re.compile(r'^\s{2,}\w[\w-]*:\s+\S')
    INDENTED_PROPS = re.compile(r'^\s{2,}\w[\w.]+\s*=\s*\S')

    while i < len(lines):
        line = lines[i]
        stripped = line.strip()

        # Skip fenced content
        if re.match(r'^```', stripped):
            result.append(line)
            i += 1
            continue

        result.append(line)
        i += 1

    return result


# ---------------------------------------------------------------------------
# Pass 8 – Final cleanup
# ---------------------------------------------------------------------------

def pass8_cleanup(lines):
    """
    - Max 2 consecutive blank lines
    - Remove trailing whitespace
    - Ensure file ends with exactly one newline
    """
    result = []
    blank_count = 0

    for line in lines:
        stripped = line.rstrip()
        if not stripped:
            blank_count += 1
            if blank_count <= 2:
                result.append('\n')
        else:
            blank_count = 0
            result.append(stripped + '\n')

    # Ensure single trailing newline
    while result and result[-1] == '\n':
        result.pop()
    result.append('\n')

    return result


# ---------------------------------------------------------------------------
# Main transformation pipeline
# ---------------------------------------------------------------------------

def transform_file(path: Path) -> dict:
    """Run all 9 passes on a single file. Returns stats dict."""
    stats = {'path': str(path), 'passes': [], 'error': None}

    try:
        content = path.read_text(encoding='utf-8-sig', errors='replace')  # utf-8-sig strips BOM
        original_lines = content.splitlines(keepends=True)

        lines = list(original_lines)

        # Track line counts between passes for reporting
        def run_pass(fn, lines, name):
            result = fn(lines)
            stats['passes'].append(name)
            return result

        lines = run_pass(pass0_strip_fences,       lines, 'P0:strip_fences')
        lines = run_pass(pass1_title_banner,        lines, 'P1:title_banner')
        lines = run_pass(pass2_fix_fence_languages, lines, 'P2:fix_languages')
        lines = run_pass(pass3_autofence,           lines, 'P3:autofence')
        lines = run_pass(pass4_fence_diagrams,      lines, 'P4:diagrams')
        lines = run_pass(pass5_gfm_tables,          lines, 'P5:gfm_tables')
        lines = run_pass(pass6_qa_headings,         lines, 'P6:qa_headings')
        lines = run_pass(pass7_wrap_configs,        lines, 'P7:wrap_configs')
        lines = run_pass(pass8_cleanup,             lines, 'P8:cleanup')

        # Write output
        output = ''.join(lines)
        path.write_text(output, encoding='utf-8')  # Write without BOM
        stats['lines_before'] = len(original_lines)
        stats['lines_after'] = len(lines)

    except Exception as e:
        stats['error'] = str(e)

    return stats


def find_md_files(root: Path):
    """Recursively find all .md files under root."""
    return sorted(root.rglob('*.md'))


def main():
    if len(sys.argv) < 2:
        print("Usage: python md_formatter_v4.py <folder_or_file>")
        sys.exit(1)

    target = Path(sys.argv[1])

    if target.is_file():
        files = [target]
    elif target.is_dir():
        files = find_md_files(target)
    else:
        print(f"ERROR: {target} is not a file or directory.")
        sys.exit(1)

    print(f"\n{'='*70}")
    print(f"  Markdown Transformation Engine v4")
    print(f"  Target: {target}")
    print(f"  Files found: {len(files)}")
    print(f"{'='*70}\n")

    successes = []
    failures = []

    for f in files:
        stats = transform_file(f)
        rel = f.relative_to(target) if target.is_dir() else f.name
        if stats['error']:
            failures.append(stats)
            print(f"  [FAIL] {rel}")
            print(f"         Error: {stats['error']}")
        else:
            successes.append(stats)
            delta = stats['lines_after'] - stats['lines_before']
            sign = '+' if delta >= 0 else ''
            print(f"  [OK]   {rel}  ({stats['lines_before']} -> {stats['lines_after']} lines, {sign}{delta})")

    print(f"\n{'='*70}")
    print(f"  SUMMARY")
    print(f"{'='*70}")
    print(f"  Total files processed : {len(files)}")
    print(f"  Successful            : {len(successes)}")
    print(f"  Failed                : {len(failures)}")
    if failures:
        print(f"\n  FAILED FILES:")
        for s in failures:
            print(f"    - {s['path']}: {s['error']}")
    print(f"\n  Done. All files are GitHub-ready Markdown.\n")


if __name__ == '__main__':
    main()
