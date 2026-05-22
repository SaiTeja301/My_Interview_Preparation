#!/usr/bin/env python3
"""
md_formatter.py  v3
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Converts plain-text-style interview notes into GitHub-flavoured
Markdown.

SAFE TO RUN MULTIPLE TIMES: Step 0 strips any existing fences
so re-runs on already-partially-formatted files are idempotent.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"""

import re, os, sys

# ───────────────────────────────────────────────────────────────
# Step 0 – Strip any existing MD fences (makes re-run safe)
# ───────────────────────────────────────────────────────────────

def strip_existing_fences(text: str) -> str:
    """Remove all ``` fenced code blocks and blockquote > markers,
    restoring raw plain text so the formatter starts clean."""
    lines = text.replace('\r\n', '\n').replace('\r', '\n').split('\n')
    out = []
    in_fence = False
    for line in lines:
        if line.strip().startswith('```'):
            in_fence = not in_fence
            continue           # drop the fence line itself
        if in_fence:
            out.append(line)   # keep content inside fences
        else:
            # strip > blockquote markers added by previous run
            cleaned = re.sub(r'^>\s*\*\*(.+)\*\*\s*$', r'\1', line)
            out.append(cleaned)
    # If file ended while still inside a fence, that's fine
    return '\n'.join(out)


# ───────────────────────────────────────────────────────────────
# Separator helpers
# ───────────────────────────────────────────────────────────────

_SEP_RE = re.compile(r'^[=\-─━═_]{4,}$')
_EQ_RE  = re.compile(r'^[=]{4,}$')
_HV_RE  = re.compile(r'^[━]{4,}$')
_DB_RE  = re.compile(r'^[═]{4,}$')
_DS_RE  = re.compile(r'^[-─]{4,}$')

def is_sep(s: str) -> bool:
    return bool(_SEP_RE.match(s.strip()))

def sep_w(s: str) -> str:
    t = s.strip()
    if _EQ_RE.match(t): return 'eq'
    if _HV_RE.match(t): return 'heavy'
    if _DB_RE.match(t): return 'dbl'
    if _DS_RE.match(t): return 'dash'
    return 'other'

# ───────────────────────────────────────────────────────────────
# Patterns
# ───────────────────────────────────────────────────────────────

_PART_RE  = re.compile(r'^(PART|TOPIC|SECTION|ROUND|CHAPTER|MODULE)\s+\d+[\s:–—\-]', re.I)
_QREF_RE  = re.compile(r'^(QUICK REFERENCE|TABLE OF CONTENTS|COMPARISON TABLE|BEST PRACTICES|INTERVIEW QUICK REFERENCE|END OF)', re.I)
_Q_RE     = re.compile(r'^\*{0,3}\s*(Q\d[\d/\-]*[.\)])\s+(.+)', re.I)
_SSECT_RE = re.compile(r'^(\d+)\.\s+(CONCEPT EXPLANATION|INTERVIEW QUESTIONS|ARCHITECTURE|COMMANDS?|BEST PRACTICES?|DIAGRAMS?|CODE|KEY POINTS?|QUICK REF|COMPARISON)\b', re.I)
_NOTE_RE  = re.compile(r'^(NOTE|IMPORTANT|BEST PRACTICE|WARNING|CAUTION|TIP|KEY POINT|REMEMBER|CRITICAL|PERFORMANCE|COMMON MISTAKE|OUTPUT)[\s:–—]+', re.I)

_TBL_BDR  = re.compile(r'^\s*\+[-=+|]+\+\s*$')
_TBL_ROW  = re.compile(r'^\s*\|')

# Code triggers: indented 2+ spaces + code-like content
_CODE_IND = re.compile(
    r'^(\s{2,})(public|private|protected|class |interface |enum |void |int |String |'
    r'List|Map|Set|@|import |package |return |if\b|for\b|while\b|try\b|catch\b|throw|'
    r'final |static |new [A-Z]|this\.|super\.|export |const |let |var |function |async |'
    r'\.pipe\(|\.subscribe\(|<\w|\{\s*$|\}\s*$|//|#!\/|this\.)'
)

# Bash-style lines (usually not indented or lightly indented)
_BASH_RE  = re.compile(
    r'^\s*(aws |kubectl |docker |helm |curl |mvn |sudo |ssh |chmod |scp |nohup |'
    r'systemctl |lsblk |mkfs |mount |EOF\b)', re.I
)

# Diagram characters
_DIAG_RE  = re.compile(r'[┌└├─│┐┘┤┬┴┼]|\+[-=]+\+')
_ARROW_RE = re.compile(r'(↓|↑|←)$|^\s+v\s*$|^\s+\^\s*$')

# ───────────────────────────────────────────────────────────────
# Language detection
# ───────────────────────────────────────────────────────────────

def guess_lang(block: str) -> str:
    if re.search(r'^\s*(aws |kubectl |docker |sudo |ssh |chmod |scp |nohup |lsblk|mkfs |mount )', block, re.M|re.I): return 'bash'
    if re.search(r'<\?xml|<dependency|<groupId|<artifactId', block): return 'xml'
    if re.search(r'\b(SELECT|INSERT|UPDATE|DELETE|CREATE TABLE|ALTER TABLE|JOIN|WHERE)\b', block, re.I): return 'sql'
    if re.search(r'(export class|@Component\(|@Injectable|@NgModule|: string|: number|: boolean)', block): return 'typescript'
    if re.search(r'(public |private |protected |class |@Service|@Bean|@Override|import java)', block): return 'java'
    if re.search(r'^\s*[\{\[]', block, re.M): return 'json'
    return 'text'

# ───────────────────────────────────────────────────────────────
# Table helpers
# ───────────────────────────────────────────────────────────────

def _cells(line: str) -> list:
    parts = line.split('|')
    c = [p.strip() for p in parts]
    while c and c[0]  == '': c.pop(0)
    while c and c[-1] == '': c.pop()
    return c

def _make_table(rows: list) -> str:
    cols = max(len(r) for r in rows)
    rows = [r + ['']*(cols-len(r)) for r in rows]
    hdr, body = rows[0], rows[1:]
    lines = ['| ' + ' | '.join(hdr) + ' |',
             '|' + '|'.join(['---']*cols) + '|']
    for r in body:
        lines.append('| ' + ' | '.join(r) + ' |')
    return '\n'.join(lines)

def try_ascii_table(lines: list, i: int) -> tuple:
    j, rows = i, []
    while j < len(lines):
        l = lines[j]
        if _TBL_BDR.match(l):       j += 1; continue
        if _TBL_ROW.match(l):       rows.append(_cells(l)); j += 1
        else: break
    if rows:
        return _make_table(rows), j
    return None, i

def try_text_table(lines: list, i: int) -> tuple:
    j, rows = i, []
    while j < len(lines):
        l = lines[j].strip()
        if re.match(r'^[-|\s:]+$', l) and '|' in l: j += 1; continue  # sep row
        if '|' in l:
            c = _cells(l)
            if c: rows.append(c); j += 1; continue
        break
    if len(rows) >= 2:
        return _make_table(rows), j
    return None, i

# ───────────────────────────────────────────────────────────────
# Heading prefix
# ───────────────────────────────────────────────────────────────

def heading_pfx(content: str, weight: str, h1_seen: list) -> str:
    c = content.strip().lstrip('*').strip()
    if _Q_RE.match(c):        return '#### '
    if _PART_RE.match(c):     return '## '
    if _QREF_RE.match(c):     return '## '
    if _SSECT_RE.match(c):    return '### '
    if weight in ('heavy','dbl'): return '## '
    if weight == 'eq':
        if not h1_seen:
            h1_seen.append(1); return '# '
        return '## '
    if weight == 'dash':
        if _Q_RE.match(c): return '#### '
        if c.upper() == c and len(c) > 4: return '### '
        return '#### '
    return '### '

# ───────────────────────────────────────────────────────────────
# Core formatter
# ───────────────────────────────────────────────────────────────

def format_md(raw: str) -> str:
    # Step 0: strip any existing markdown fences (idempotent safety)
    text  = strip_existing_fences(raw)
    lines = text.split('\n')
    n     = len(lines)
    out: list[str] = []
    h1_seen: list  = []

    # States
    NORM, CODE, DIAG = 'N', 'C', 'D'
    state = NORM
    buf: list[str] = []

    def emit(s: str = ''):
        out.append(s)

    def flush_buf(kind: str):
        if not buf: return
        if kind == CODE:
            lang = guess_lang('\n'.join(buf))
            # dedent
            nonempty = [l for l in buf if l.strip()]
            indent = min((len(l)-len(l.lstrip()) for l in nonempty), default=0)
            emit(f'```{lang}')
            for cl in buf:
                emit(cl[indent:] if len(cl) >= indent else cl)
            emit('```')
            emit('')
        elif kind == DIAG:
            emit('```text')
            for dl in buf:
                emit(dl.rstrip())
            emit('```')
            emit('')
        buf.clear()

    def last_out() -> str:
        return out[-1] if out else ''

    def ensure_blank():
        if last_out() != '':
            emit()

    i = 0
    while i < n:
        raw_l   = lines[i]
        stripped = raw_l.strip()

        # ── CODE state ────────────────────────────────────────────
        if state == CODE:
            if stripped == '':
                # peek: if next non-blank is still code-like, keep going
                j = i+1
                while j < n and lines[j].strip() == '': j += 1
                next_l = lines[j] if j < n else ''
                if (_CODE_IND.match(next_l) or _BASH_RE.match(next_l)
                        or (next_l.startswith('  ') and next_l.strip()
                            and not is_sep(next_l.strip()))):
                    buf.append('')
                else:
                    flush_buf(CODE); state = NORM; emit()
            else:
                buf.append(raw_l.rstrip())
            i += 1; continue

        # ── DIAGRAM state ─────────────────────────────────────────
        if state == DIAG:
            if stripped == '':
                j = i+1
                while j < n and lines[j].strip() == '': j += 1
                next_l = lines[j] if j < n else ''
                if _DIAG_RE.search(next_l) or _ARROW_RE.search(next_l):
                    buf.append('')
                else:
                    flush_buf(DIAG); state = NORM; emit()
            else:
                buf.append(raw_l.rstrip())
            i += 1; continue

        # ── NORM state ────────────────────────────────────────────

        # blank
        if stripped == '':
            if last_out() != '': emit()
            i += 1; continue

        # ASCII table border
        if _TBL_BDR.match(raw_l):
            md, end = try_ascii_table(lines, i)
            if md:
                ensure_blank()
                out.extend(md.split('\n'))
                emit()
                i = end; continue

        # separator → heading
        if is_sep(stripped):
            sw = sep_w(stripped)
            j  = i+1
            while j < n and lines[j].strip() == '': j += 1
            if j < n:
                ns = lines[j].strip()
                if is_sep(ns): i += 1; continue      # two consecutive seps
                if ns:
                    # collect until closing sep
                    k = j+1
                    while k < n and lines[k].strip() == '': k += 1
                    closing = k < n and is_sep(lines[k].strip())
                    content = ns.lstrip('*').strip()
                    pfx = heading_pfx(content, sw, h1_seen)
                    ensure_blank()
                    emit(f'{pfx}{content}')
                    emit()
                    i = j+1
                    if closing: i = k+1
                    continue
            i += 1; continue

        # PART/TOPIC/SECTION without separator
        if _PART_RE.match(stripped) or _QREF_RE.match(stripped):
            ensure_blank()
            emit(f'## {stripped.lstrip("*").strip()}')
            emit(); i += 1; continue

        # Q-numbered line without separator
        qm = _Q_RE.match(stripped)
        if qm:
            ensure_blank()
            emit(f'#### {qm.group(1)} {qm.group(2).strip()}')
            emit(); i += 1; continue

        # Sub-section label
        if _SSECT_RE.match(stripped):
            ensure_blank()
            emit(f'### {stripped}')
            emit(); i += 1; continue

        # Note / callout
        if _NOTE_RE.match(stripped):
            ensure_blank()
            emit(f'> **{stripped}**')
            emit(); i += 1; continue

        # Pipe table (text-style, two rows + separator)
        if '|' in stripped and not stripped.startswith('|---'):
            j = i+1
            if j < n and re.match(r'^[\s\-|:]+$', lines[j]) and '|' in lines[j]:
                md, end = try_text_table(lines, i)
                if md and end > i+1:
                    ensure_blank()
                    out.extend(md.split('\n'))
                    emit()
                    i = end; continue

        # Diagram line
        if _DIAG_RE.search(stripped) or _ARROW_RE.search(stripped.rstrip()):
            flush_buf(CODE)
            state = DIAG
            buf.append(raw_l.rstrip())
            i += 1; continue

        # Indented code / bash
        if _CODE_IND.match(raw_l) or _BASH_RE.match(raw_l):
            state = CODE
            buf.append(raw_l.rstrip())
            i += 1; continue

        # Bullet arrow
        ba = re.match(r'^(?:->|→|==>)\s+(.+)$', stripped)
        if ba:
            emit(f'- {ba.group(1).strip()}')
            i += 1; continue

        # Plain line – clean up lone *** or separator chars that slipped through
        clean = stripped.lstrip('*').strip()
        if re.match(r'^[*\-─━═=]{3,}$', clean):
            i += 1; continue
        emit(clean)
        i += 1

    # flush remaining
    flush_buf(state)

    result = '\n'.join(out)
    result = re.sub(r'\n{3,}', '\n\n', result)
    return result.strip() + '\n'


# ───────────────────────────────────────────────────────────────
# File / folder
# ───────────────────────────────────────────────────────────────

def process_file(path: str) -> tuple:
    try:
        with open(path, 'r', encoding='utf-8') as f:
            orig = f.read()
        new = format_md(orig)
        with open(path, 'w', encoding='utf-8') as f:
            f.write(new)
        return True, orig.count('\n'), new.count('\n'), None
    except Exception as e:
        import traceback
        return False, 0, 0, traceback.format_exc()


def process_folder(root: str):
    md_files = []
    for dp, _, fnames in os.walk(root):
        for fname in fnames:
            if fname.endswith('.md'):
                md_files.append(os.path.join(dp, fname))
    md_files.sort()

    total = len(md_files); success = 0; failed = []
    print('='*72)
    print('  MARKDOWN FORMATTER v3 -- GitHub-Renderable MD Transformation')
    print(f'  Root: {root}')
    print('='*72)
    print(f'\n  Total .md files found: {total}\n')
    print('-'*72)

    for fpath in md_files:
        rel = os.path.relpath(fpath, root)
        ok, ol, nl, err = process_file(fpath)
        if ok:
            d = nl - ol; sign = '+' if d >= 0 else ''
            print(f'  [OK]   {rel}')
            print(f'         Lines: {ol} -> {nl}  ({sign}{d})')
            success += 1
        else:
            print(f'  [FAIL] {rel}')
            print(f'         {err}')
            failed.append((rel, err))

    print()
    print('='*72)
    print('  FINAL SUMMARY')
    print('='*72)
    print(f'  Total   : {total}')
    print(f'  OK      : {success}')
    print(f'  Failed  : {len(failed)}')
    if failed:
        for f, e in failed:
            print(f'    - {f}: {e}')
    print('='*72)


if __name__ == '__main__':
    target = (sys.argv[1] if len(sys.argv) > 1
              else r'E:\Teja_Interview_preparation\My_Interview_Preparation\Java_jdbc_hibernate_SpringBoot\analysis')
    if os.path.isfile(target):
        ok, ol, nl, err = process_file(target)
        if ok:
            d = nl - ol; sign = '+' if d >= 0 else ''
            print(f'[OK]  {target}')
            print(f'      Lines: {ol} -> {nl}  ({sign}{d})')
        else:
            print(f'[FAIL] {target}: {err}')
    else:
        process_folder(target)
