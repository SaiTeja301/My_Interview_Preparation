# Building with the Claude API — Certification Preparation Notes

> **Role:** Expert AI Trainer | Certification Mentor | Technical Content Architect
> **Scope:** Claude API Fundamentals · Claude Code · Claude Hooks · MCP Advanced Topics (Sampling, Roots, Transports, Notifications)
> **Output:** Structured for Quick Revision · Interview Prep · Certification Exam

---

## 📚 Table of Contents

1. [Course / Topic Overview](#1-course--topic-overview)
2. [Core Concepts](#2-core-concepts)
3. [Important Definitions](#3-important-definitions)
4. [Tools & Technologies](#4-tools--technologies)
5. [Architecture / Workflows](#5-architecture--workflows)
6. [Key Certification Topics](#6-key-certification-topics)
7. [Important Commands / Code Examples](#7-important-commands--code-examples)
8. [Real-World Use Cases](#8-real-world-use-cases)
9. [Interview Questions & Answers](#9-interview-questions--answers)
10. [Certification Questions](#10-certification-questions)
11. [Revision Notes](#11-revision-notes)
12. [One-Page Summary](#12-one-page-summary)
13. [Top 20 Key Takeaways](#13-top-20-key-takeaways)

---

## 1. Course / Topic Overview

### 📌 Beginner Explanation
This course teaches developers how to **build production-grade applications** using the Anthropic Claude API. It covers the fundamentals of API communication, conversational AI, and extends into advanced tooling like **Claude Code** (an agentic terminal assistant) and **MCP Advanced Topics** (Sampling, Roots, Transports, and Notifications).

### 📌 Intermediate Understanding
The course covers:
- The complete **5-step request lifecycle** from client app → server → Claude API → model → response
- **Multi-turn conversation management**, system prompts, temperature, and streaming
- **Structured output extraction** via prefilling and stop sequences
- **Claude Code** as an agentic coding assistant with built-in tools, hooks, GitHub integration, and MCP server support
- **MCP Advanced Topics**: Sampling, Roots, JSON message protocol, Stdio/StreamableHTTP transports, and Notifications

### 📌 Advanced Insights
At advanced level:
- **Hooks** (PreToolUse/PostToolUse/Stop/SubagentStop/Notification/etc.) enable automated workflows, access control, and code quality enforcement
- **Sampling** inverts the MCP server architecture — the server delegates AI calls to the client
- **Roots** provide security-bounded file system access with path validation
- **StreamableHTTP** transport enables remote MCP servers with SSE-based bidirectional communication
- **stateless_http** and **json_response** flags control production scaling behavior at the cost of features
- **Agent SDK** enables programmatic control of Claude Code from code

---

## 2. Core Concepts

### 🔑 2.1 The 5-Step Request Lifecycle

```
[Client App] ──► [Your Server] ──► [Anthropic API] ──► [Claude Model]
                     ▲                                          │
                     └──────────── Response ◄──────────────────┘
```

| Step | Name | Description |
|------|------|-------------|
| 1 | **Request to Server** | Client app sends user input to your backend server |
| 2 | **Request to Anthropic API** | Server calls Claude via SDK or HTTP |
| 3 | **Model Processing** | Tokenize → Embed → Contextualize → Generate |
| 4 | **Response to Server** | API sends back text + usage + stop_reason |
| 5 | **Response to Client** | Server forwards result to the client application |

> ⚠️ **CERT ALERT:** API keys must NEVER be exposed in client-side code. Always route calls through a server.

---

### 🔑 2.2 Inside Claude — Text Generation (4 Stages)

| Stage | What Happens |
|-------|-------------|
| **Tokenization** | Input text is broken into tokens (words, sub-words, symbols) |
| **Embedding** | Each token → numerical vector capturing all possible meanings |
| **Contextualization** | Surrounding words refine each embedding (attention mechanism) |
| **Generation** | Probabilities computed for next token; temperature controls sampling |

**When does Claude stop generating?**
1. `max_tokens` limit reached
2. Natural end-of-sequence token generated
3. A `stop_sequence` string is encountered

---

### 🔑 2.3 Messages and Roles
- Claude is **stateless** — no memory between calls; you must send full history
- Two roles: `"user"` (human input) and `"assistant"` (Claude's responses)
- Each message is a dict: `{"role": "user", "content": "text..."}`

---

### 🔑 2.4 System Prompts
- Control Claude's **tone, persona, and behavior**
- Passed as the `system` parameter (NOT inside messages list)
- Claude API does **not accept `system=None`** — conditionally include it
- Gets included in every request → functions like a persistent context

---

### 🔑 2.5 Temperature

| Range | Use Case |
|-------|----------|
| **0.0 – 0.3** | Factual responses, coding, data extraction, content moderation |
| **0.4 – 0.7** | Summarization, educational content, problem-solving |
| **0.8 – 1.0** | Brainstorming, creative writing, marketing, joke generation |

> Temperature does NOT guarantee different outputs — it only changes probability distributions.

---

### 🔑 2.6 Response Streaming
- **Problem:** Without streaming, users wait 10-30 seconds in silence
- **Solution:** Stream events arrive chunk by chunk, text appears word-by-word

**Stream Event Sequence:**
```
MessageStart → ContentBlockStart → ContentBlockDelta (actual text) 
           → ContentBlockStop → MessageDelta → MessageStop
```

- `ContentBlockDelta` = the actual text chunks to display
- After streaming: call `stream.get_final_message()` for storage/processing
- In production: forward via **WebSockets** or **Server-Sent Events (SSE)**

---

### 🔑 2.7 Structured Output — Prefill + Stop Sequences
- **Problem:** Claude wraps JSON in markdown and adds explanatory text
- **Solution:** Prefill an assistant message + use stop sequences

**How it works:**
1. User message: "Generate JSON"
2. Prefill: `"```json"` — Claude thinks it already started a code block
3. Claude continues writing ONLY JSON content
4. Stop sequence: `"```"` — stops generation before the closing backticks
5. Result: **clean JSON** with no extra formatting

---

### 🔑 2.8 Claude Code — Agentic Coding Assistant
**How Coding Assistants Work (3-Step Loop):**

> **IMAGE INSIGHT (img1):** Shows the Agent Loop: Task → Language Model (with Set of Tools) → [Gather Context → Formulate a Plan → Take an Action] → Iterate

1. **Gather Context** — understand error/task, identify relevant files
2. **Formulate a Plan** — decide how to solve the issue
3. **Take Action** — implement by reading/writing files, running commands

**Tool Use under the hood:**
- Language models can only process/return text
- Coding assistants inject tool instructions into the prompt
- Model responds "ReadFile: main.go" → assistant executes the action → sends result back → model answers

---

### 🔑 2.9 Claude Code — Context Management
**CLAUDE.md Files (3 Types):**

| File | Scope | Use |
|------|-------|-----|
| `CLAUDE.md` | Project (committed) | Architecture, style, important commands — shared with team |
| `CLAUDE.local.md` | Project (not committed) | Personal customizations |
| `~/.claude/CLAUDE.md` | All projects globally | Universal instructions across all projects |

**Key Commands:**
| Command | Purpose |
|---------|---------|
| `/init` | Analyze codebase, create CLAUDE.md summary |
| `/memory` | Open CLAUDE.md for editing |
| `/plan` | Enable Planning Mode for complex tasks |
| `/effort` | Control reasoning depth (low/medium/max) |
| `/compact` | Summarize conversation, retain key knowledge |
| `/clear` | Start fresh conversation (previous saved with /resume) |
| `/rewind` | Jump back to earlier point in conversation |
| `/hooks` | Write/manage hooks via command |
| `/install-github-app` | Set up Claude Code GitHub integration |

---

### 🔑 2.10 Claude Code Hooks

> **IMAGE INSIGHT (img2):** Hook Definition locations: Global (`~/.claude/settings.json`), Project (`.claude/settings.json`), Project-not-committed (`.claude/settings.local.json`). Can write by hand or with `/hooks` command.

> **IMAGE INSIGHT (img3):** PreToolUse runs BEFORE the tool call — can BLOCK it by sending error to Claude. PostToolUse runs AFTER — too late to block, but can provide additional feedback.

> **IMAGE INSIGHT (img4):** Building a Hook — 4 steps: (1) PreToolUse or PostToolUse, (2) Which tool to watch, (3) Write command to receive JSON, (4) Provide feedback via exit code.

**Hook Types:**

| Hook Type | Trigger | Can Block? |
|-----------|---------|-----------|
| **PreToolUse** | Before tool executes | ✅ Yes (exit code 2) |
| **PostToolUse** | After tool executes | ❌ No (feedback only) |
| **Notification** | When permission needed or idle 60s | — |
| **Stop** | When Claude finishes responding | — |
| **SubagentStop** | When a subagent finishes | — |
| **PreCompact** | Before compact operation | — |
| **UserPromptSubmit** | When user submits prompt | — |
| **SessionStart** | When session starts/resumes | — |
| **SessionEnd** | When session ends | — |

**Exit Codes:**
- `0` = Allow tool call to proceed
- `2` = Block tool call (PreToolUse only); stderr message sent back to Claude

> **IMAGE INSIGHT (img5):** Security Best Practices for hooks: (1) Validate/sanitize inputs, (2) Quote shell variables "$VAR", (3) Block path traversal (check for `..`), (4) Use absolute paths, (5) Skip sensitive files (.env, .git/, keys)

---

### 🔑 2.11 MCP Sampling
**The Problem Sampling Solves:**
- MCP servers need to summarize/process data using Claude
- Option 1: Give server its own API key (complex + costly)
- Option 2 (Sampling): Server asks the **client** to call Claude on its behalf

**Sampling Flow:**

> **IMAGE INSIGHT (img7, Advanced Topics):** Option 1 — Give MCP Server access to Claude (server calls Claude directly). Option 2 (img Advanced Topics2) — Server generates prompt, asks client "Could you call Claude for me?", client calls Claude, returns result to server.

```
Server completes work → Creates prompt → Sends sampling request to Client
→ Client calls Claude → Returns generated text → Server uses result
```

**Benefits:**
- No API keys needed on server side
- Cost shifts to client (server doesn't pay for AI usage)
- Ideal for public/multi-tenant MCP servers

---

### 🔑 2.12 MCP Roots
**The Problem Roots Solve:**
- Without roots, Claude can't find files without full paths
- Users don't want to type full file paths every time

**Roots Flow:**
1. User says "convert biking.mp4 to MOV"
2. Claude calls `list_roots` → sees approved directories
3. Claude calls `read_dir` → searches accessible directories
4. Finds file → calls conversion tool with full path

**Security Benefits:**
- Limits server to approved directories only
- Access outside roots returns an error
- SDK doesn't enforce roots automatically — you must implement `is_path_allowed()` checks

---

### 🔑 2.13 MCP Message Protocol

> **IMAGE INSIGHT (MCP Advanced Topics3):** Shows JSON-RPC 2.0 message format: `CallToolRequest` with `method: "tools/call"`, `params: {name, arguments}` → `CallToolResult` with `content` array and `isError: false`

**Two Message Categories:**

| Category | Examples |
|----------|---------|
| **Request-Result Pairs** | Call Tool, List Prompts, Read Resource, Initialize |
| **Notification (one-way)** | Progress, Logging, Tool List Changed, Resource Updated |

**MCP 3-Message Handshake:**
```
Client → Initialize Request
Server → Initialize Result (with capabilities)
Client → Initialized Notification (no response expected)
```

---

### 🔑 2.14 MCP Transports

**Stdio Transport:**
- Client launches server as a subprocess
- Communication via stdin/stdout streams
- Both parties can send at any time
- Only works on **same machine**
- Best for local development and testing

> **IMAGE INSIGHT (MCP Advanced Topics4):** StreamableHTTP Transport — MCP Client on "Your Computer" connects to MCP Server on "Remote Machine" via Streamable HTTP Transport (bidirectional arrows)

**StreamableHTTP Transport:**
- Enables remote MCP servers over HTTP
- Uses **Server-Sent Events (SSE)** for server→client communication

> **IMAGE INSIGHT (MCP Advanced Topics5):** Initialize flow — Client sends Initialize Request → Server responds with Initialize Result including `mcp-session-id` header → Client sends Initialized Notification with session ID (no result expected)

> **IMAGE INSIGHT (MCP Advanced Topics6):** Dual SSE Connection model — Primary SSE connection for server-initiated requests (open indefinitely). Tool-specific SSE connection created per tool call, closes when CallToolResult is sent.

> **IMAGE INSIGHT (MCP Advanced Topics7):** Message routing — Progress Notification via Primary SSE. Logging + Tool Result via Tool-Specific SSE response.

**Dual SSE Connection Model:**

| SSE Connection | Purpose | Lifetime |
|---------------|---------|---------|
| **Primary SSE** | Server→Client requests, sampling, notifications | Open indefinitely |
| **Tool-Specific SSE** | Progress + logs for a specific tool call + final result | Closes after CallToolResult |

---

### 🔑 2.15 StreamableHTTP — stateless_http & json_response Flags

| Flag | Effect When True |
|------|-----------------|
| `stateless_http=True` | No session IDs; no server→client requests; no sampling; no progress; enables horizontal scaling |
| `json_response=True` | No streaming; only final JSON result returned (no intermediate notifications) |

**When to use `stateless_http=True`:**
- Horizontal scaling behind a load balancer (multiple server instances)
- Tool doesn't require AI model sampling
- No server-to-client communication needed

---

### 🔑 2.16 Agent SDK (Programmatic Claude Code)
- Package: `@anthropic-ai/claude-agent-sdk`
- Supports: custom system prompts, MCP servers, hooks, subagents, session resumption
- Returns stream of JSON messages (same events as CLI)

---

## 3. Important Definitions

| Term | Definition |
|------|-----------|
| **Token** | Smallest unit Claude processes — word, sub-word, or symbol |
| **Embedding** | Numerical vector representing all possible meanings of a token |
| **Contextualization** | Adjusting embeddings based on surrounding words (attention mechanism) |
| **Temperature** | Decimal 0–1 controlling randomness/creativity in generation |
| **Stop Sequence** | A string pattern that triggers Claude to halt generation immediately |
| **Prefilling** | Adding an assistant message at end of messages to steer output format |
| **System Prompt** | Plain string defining Claude's persona/role/behavior; passed as `system` parameter |
| **Streaming** | Receiving response tokens chunk-by-chunk instead of waiting for completion |
| **Claude Code** | Anthropic's terminal-based agentic coding assistant |
| **CLAUDE.md** | Persistent context/instruction file read at the start of every Claude Code session |
| **Hooks** | Commands that run before/after Claude tool calls to intercept, validate, or extend behavior |
| **PreToolUse** | Hook type that runs BEFORE a tool executes; can block execution with exit code 2 |
| **PostToolUse** | Hook type that runs AFTER a tool executes; cannot block but can provide feedback |
| **Sampling** | MCP feature where the server requests the client to call an LLM on its behalf |
| **Roots** | Directory permission boundaries allowing MCP servers to access specific file paths |
| **is_path_allowed()** | Developer-implemented function to validate file access against MCP roots |
| **MCP Transport** | Communication channel between MCP Client and Server (Stdio, StreamableHTTP) |
| **Stdio Transport** | MCP communication via subprocess stdin/stdout (same machine only) |
| **StreamableHTTP** | MCP transport for remote servers over HTTP with SSE for bidirectional communication |
| **SSE (Server-Sent Events)** | Long-lived HTTP responses used for server→client message streaming |
| **mcp-session-id** | Session identifier header sent by StreamableHTTP server for client tracking |
| **stateless_http** | Flag eliminating session tracking to enable horizontal scaling |
| **json_response** | Flag disabling streaming; returns only final JSON result |
| **Agent SDK** | Library (`@anthropic-ai/claude-agent-sdk`) for programmatically running Claude Code |
| **Planning Mode** | Claude Code mode that reads more files and creates an implementation plan before acting |
| **ultrathink** | Keyword to signal Claude to reason more deeply on a single turn |
| **$ARGUMENTS** | Placeholder in custom command files that gets replaced with user input |
| **SamplingMessage** | MCP type for messages in a sampling request |
| **CreateMessageResult** | MCP type for the response from a sampling callback |
| **ListRootsResult** | MCP type containing the array of approved root directories |

---

## 4. Tools & Technologies

### 🛠️ 4.1 Anthropic Python SDK

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | `anthropic` / `anthropic[vertex]` |
| **Key Class** | `Anthropic`, `AsyncAnthropic` |
| **Key Method** | `client.messages.create()`, `client.messages.stream()` |
| **Install** | `pip install anthropic` |
| **When to Use** | All Claude API calls from Python server-side code |

---

### 🛠️ 4.2 Claude Code

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | Claude Code (`claude`) |
| **Type** | Terminal-based agentic coding assistant |
| **Install (Mac/Linux/WSL)** | `curl -fsSL https://claude.ai/install.sh \| bash` |
| **Install (Windows PowerShell)** | `irm https://claude.ai/install.ps1 \| iex` |
| **Install (npm legacy)** | `npm install -g @anthropic-ai/claude-code` |
| **Start** | Run `claude` in terminal |
| **Built-in Tools** | File read/write/search, terminal commands, web access, MCP server support |
| **Platform Support** | MacOS, Windows WSL, Linux |
| **When to Use** | AI-assisted development directly in terminal |

---

### 🛠️ 4.3 Agent SDK

| Attribute | Detail |
|-----------|--------|
| **Package** | `@anthropic-ai/claude-agent-sdk` |
| **Language** | TypeScript/JavaScript (Python also available) |
| **Key Function** | `query({ prompt, options })` |
| **When to Use** | Running Claude Code programmatically from scripts or apps |
| **Example** | `for await (const msg of query({ prompt })) { ... }` |

---

### 🛠️ 4.4 MCP Python SDK (FastMCP)

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | `mcp` Python SDK |
| **Key Class** | `FastMCP`, `Context` |
| **Key Imports** | `SamplingMessage`, `TextContent`, `CreateMessageResult`, `CreateMessageRequestParams` |
| **Sampling Function** | `ctx.session.create_message(messages, max_tokens, system_prompt)` |
| **Roots Function** | `ctx.session.list_roots()` |
| **Notifications** | `ctx.info()`, `ctx.report_progress()` |

---

### 🛠️ 4.5 Playwright MCP Server

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | Playwright MCP Server |
| **Install** | `claude mcp add playwright npx @playwright/mcp@latest` |
| **Purpose** | Gives Claude Code browser automation capabilities |
| **Permission Allow** | `mcp__playwright` in `.claude/settings.local.json` |
| **Use Case** | Visual testing, UI review, automated browser workflows |

---

### 🛠️ 4.6 GitHub Integration for Claude Code

| Attribute | Detail |
|-----------|--------|
| **Setup Command** | `/install-github-app` inside Claude Code |
| **Default Workflows** | Mention Action (`@claude`), PR Review Action |
| **Workflow Location** | `.github/workflows/` |
| **MCP in Actions** | Configure via `mcp_config` YAML block |
| **Permissions** | Each tool must be explicitly listed in `allowed_tools` |

---

### 🛠️ 4.7 python-dotenv

| Attribute | Detail |
|-----------|--------|
| **Package** | `python-dotenv` |
| **Purpose** | Load environment variables from `.env` file |
| **Usage** | `from dotenv import load_dotenv; load_dotenv()` |
| **Security Rule** | Always add `.env` to `.gitignore` |

---

## 5. Architecture / Workflows

### 🏗️ 5.1 Complete API Request Architecture

```
[Client App]
    │  (User message)
    ▼
[Your Server]──[Anthropic SDK]──► [Anthropic API / Vertex AI]
    │                                        │
    │◄──── Response (text + usage + stop_reason) ────────────
    ▼
[Client App] (display response)
```

**Required API Parameters:**
- `model` — Claude model name (e.g., `claude-sonnet-4-0`)
- `max_tokens` — Response budget ceiling (NOT a target)
- `messages` — Full conversation history array

---

### 🏗️ 5.2 Structured Output Flow (Prefill + Stop Sequence)

```
User Message: "Generate a JSON object"
    │
Add Assistant Prefill: "```json"
    │
Claude generates ONLY JSON content (thinks code block already started)
    │
Stop Sequence "```" triggers → generation stops
    │
Result: Clean JSON only, no markdown, no explanation
    │
json.loads(text.strip()) → parsed Python dict
```

---

### 🏗️ 5.3 Claude Code Agent Loop

```
User Task (e.g., "Fix this bug: Cannot read property 'records'")
    │
    ▼
[Language Model + Set of Tools]
    │
    ├─ Gather Context (Read files, search codebase)
    │
    ├─ Formulate a Plan (Identify root cause, plan fix)
    │
    └─ Take an Action (Edit files, run tests)
         │
         └─ Iterate (repeat until task complete)
```

---

### 🏗️ 5.4 Hook Execution Flow

```
User asks Claude → Claude decides to use a tool
    │
    ▼
PreToolUse Hook runs (if configured for that tool)
    │
    ├─ Exit 0 → Tool proceeds normally
    └─ Exit 2 → Tool BLOCKED; stderr message sent to Claude
         │
         ▼
Claude Code executes the tool (if allowed)
    │
    ▼
PostToolUse Hook runs (if configured)
    │
    └─ Provides feedback/runs follow-up commands
```

**Hook configuration JSON structure:**
```json
{
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "Read",
        "hooks": [{"type": "command", "command": "node /path/read_hook.js"}]
      }
    ],
    "PostToolUse": [
      {
        "matcher": "Write|Edit|MultiEdit",
        "hooks": [{"type": "command", "command": "node /path/edit_hook.js"}]
      }
    ]
  }
}
```

---

### 🏗️ 5.5 MCP Sampling Architecture

```
[Your App / MCP Client]                    [External Data Source]
        │                                          │
        │────── Tool Call Request ──────► [MCP Server]──► Wikipedia/API
        │                                          │
        │◄─── "Could you call Claude for me?" ─────┘
        │
        │────── Calls Claude (Anthropic API) ──────────────►
        │◄────── Claude's response ────────────────────────
        │
        │────── Returns Claude's response to MCP Server ───►
        │◄─── Final Tool Result ────────────────────────────
```

---

### 🏗️ 5.6 MCP Roots — File Access Flow

```
User: "Convert biking.mp4 to MOV"
    │
    ▼
Claude → list_roots() → ["/Users/me/Movies"]
    │
    ▼
Claude → read_dir("/Users/me/Movies") → ["biking.mp4", "vacation.mp4"]
    │
    ▼
Claude → convert_video("/Users/me/Movies/biking.mp4", "mov")
    │
    ▼
Server calls is_path_allowed() → checks path against roots → ✅ Allowed
```

---

### 🏗️ 5.7 StreamableHTTP Connection Sequence

```
Client ─── Initialize Request ──────────────────────────────────────► Server
Client ◄── Initialize Result (mcp-session-id: 7089d4160bbb4d) ────────
Client ─── Initialized Notification (with session ID) ──────────────► Server
                               [No response expected]

Client ◄──────── Primary SSE Response (held open indefinitely) ────── Server
                [Used for server→client requests, notifications]

Client ─── POST /mcp/ (CallToolRequest + mcp-session-id) ──────────► Server
Client ◄──────── Tool-Specific SSE (Progress + Result) ─────────────
                [Closes after CallToolResult sent]
```

---

### 🏗️ 5.8 MCP Logging & Notifications Pattern

```python
@mcp.tool()
async def research(topic: str, *, context: Context):
    await context.info("Starting research...")          # Logging notification
    await context.report_progress(20, 100)              # Progress notification
    sources = await do_research(topic)
    
    await context.info("Writing report...")
    await context.report_progress(70, 100)
    results = await generate_report(sources)
    
    return results
```

**Client-side callbacks:**
```python
async def logging_callback(params: LoggingMessageNotificationParams):
    print(params.data)

async def progress_callback(progress: float, total: float | None, message: str | None):
    percentage = (progress / total) * 100
    print(f"Progress: {progress}/{total} ({percentage:.1f}%)")
```

---

## 6. Key Certification Topics

### CERT TOPIC 1: API Setup (Step-by-Step)
1. Navigate to `console.anthropic.com` → Create API Key
2. Store in `.env` file → load with `python-dotenv`
3. Install: `pip install anthropic python-dotenv`
4. Initialize: `client = Anthropic()` (auto-reads ANTHROPIC_API_KEY env var)
5. **NEVER** expose API key in client-side code

### CERT TOPIC 2: Required vs Optional Parameters

| Parameter | Required | Description |
|-----------|----------|-------------|
| `model` | ✅ | Claude model name |
| `max_tokens` | ✅ | Response length budget |
| `messages` | ✅ | Conversation history list |
| `system` | ❌ | Role/persona for Claude |
| `temperature` | ❌ | Creativity dial (0–1) |
| `stop_sequences` | ❌ | Halt generation triggers |
| `stream` | ❌ | Enable streaming |

### CERT TOPIC 3: Claude Code CLAUDE.md Locations

| File | Location | Committed? | Scope |
|------|----------|-----------|-------|
| `CLAUDE.md` | Project root | ✅ Yes | Shared with team |
| `CLAUDE.local.md` | Project root | ❌ No | Personal customizations |
| `~/.claude/CLAUDE.md` | Home directory | N/A | All projects on machine |

### CERT TOPIC 4: Hook Exit Codes
- **Exit 0** = Proceed normally
- **Exit 2** = Block tool call (PreToolUse ONLY)
- stderr output from exit code 2 = message Claude sees explaining the block

### CERT TOPIC 5: Hook Security Rules (img5)
1. **Validate and sanitize inputs** — Never trust input data blindly
2. **Always quote shell variables** — Use `"$VAR"` not `$VAR`
3. **Block path traversal** — Check for `..` in file paths
4. **Use absolute paths** — Specify full paths for scripts
5. **Skip sensitive files** — Avoid `.env`, `.git/`, keys

### CERT TOPIC 6: MCP Sampling — When to Use
- Use when building **publicly accessible MCP servers**
- Prevents server from racking up AI costs for every user
- Server needs AI capabilities WITHOUT its own API key
- Shifts cost responsibility to the client

### CERT TOPIC 7: MCP Roots — Key Rules
- Roots are **not automatically enforced** by MCP SDK
- Must implement custom `is_path_allowed()` with `list_roots()` check
- Prevents accidental access to sensitive files
- Path must exist and be within an approved root directory

### CERT TOPIC 8: Transport Comparison

| Feature | Stdio | StreamableHTTP | Stateless HTTP |
|---------|-------|---------------|----------------|
| Same machine required | ✅ | ❌ (remote OK) | ❌ |
| Server→Client requests | ✅ | ✅ (via SSE) | ❌ |
| Sampling support | ✅ | ✅ | ❌ |
| Progress notifications | ✅ | ✅ | ❌ |
| Horizontal scaling | ❌ | ❌ | ✅ |
| Session ID required | ❌ | ✅ | ❌ |

### CERT TOPIC 9: StreamableHTTP Configuration Flags

| Flag | Default | When True |
|------|---------|-----------|
| `stateless_http` | False | No sessions, no server→client, no sampling — enables load balancing |
| `json_response` | False | No streaming, only final JSON result |

### CERT TOPIC 10: Claude Code — Planning Mode vs Effort Level

| Feature | Purpose | When to Use |
|---------|---------|------------|
| **Planning Mode** (`/plan`) | Broad codebase exploration + approval before action | Multi-file, multi-step tasks |
| **Effort Level** (`/effort`) | Deep reasoning on complex logic | Algorithmic problems, debugging |
| **ultrathink** | Extra deep reasoning for single turn | One-off complex decisions |

---

## 7. Important Commands / Code Examples

### 7.1 Basic API Setup
```python
from dotenv import load_dotenv
load_dotenv()
from anthropic import Anthropic

client = Anthropic()  # Auto-reads ANTHROPIC_API_KEY env var
model = "claude-sonnet-4-0"
```

### 7.2 First API Request
```python
message = client.messages.create(
    model=model,
    max_tokens=1000,
    messages=[{"role": "user", "content": "What is quantum computing? One sentence."}]
)
print(message.content[0].text)      # Extracted text response
print(message.usage)                # Token usage stats
print(message.stop_reason)          # Why generation stopped
```

### 7.3 Flexible Chat Function
```python
def add_user_message(messages, text):
    messages.append({"role": "user", "content": text})

def add_assistant_message(messages, text):
    messages.append({"role": "assistant", "content": text})

def chat(messages, system=None, temperature=1.0, stop_sequences=[]):
    params = {
        "model": model,
        "max_tokens": 1000,
        "messages": messages,
        "temperature": temperature,
        "stop_sequences": stop_sequences,
    }
    if system:
        params["system"] = system   # Never pass system=None to API
    message = client.messages.create(**params)
    return message.content[0].text
```

### 7.4 System Prompt Usage
```python
system_prompt = """
You are a patient math tutor.
Do not directly answer a student's questions.
Guide them to a solution step by step.
"""
answer = chat(messages, system=system_prompt)
```

### 7.5 Temperature Control
```python
# Factual/deterministic
answer = chat(messages, temperature=0.0)

# Creative/varied  
answer = chat(messages, temperature=1.0)
```

### 7.6 Streaming Implementation
```python
# Simple streaming (display only)
with client.messages.stream(
    model=model, max_tokens=1000, messages=messages
) as stream:
    for text in stream.text_stream:
        print(text, end="", flush=True)

# Streaming + save complete message
with client.messages.stream(
    model=model, max_tokens=1000, messages=messages
) as stream:
    for text in stream.text_stream:
        pass  # Forward to client via WebSocket/SSE
    final_message = stream.get_final_message()  # For database storage
```

### 7.7 Structured JSON Output (Prefill + Stop Sequence)
```python
import json

messages = []
add_user_message(messages, "Generate an AWS EventBridge rule as JSON")
add_assistant_message(messages, "```json")  # Prefill = Claude continues from here

text = chat(messages, stop_sequences=["```"])  # Stop before closing backticks
clean_json = json.loads(text.strip())
```

### 7.8 Raw Streaming Events
```python
stream = client.messages.create(
    model=model, max_tokens=1000, messages=messages, stream=True
)
for event in stream:
    print(type(event).__name__, event)  # Inspect all event types
```

### 7.9 Claude Code Installation Commands
```bash
# MacOS/Linux/WSL
curl -fsSL https://claude.ai/install.sh | bash

# Windows PowerShell
irm https://claude.ai/install.ps1 | iex

# MacOS Homebrew
brew install --cask claude-code

# Start
claude

# Add MCP server
claude mcp add playwright npx @playwright/mcp@latest
```

### 7.10 Claude Code Commands Reference
```
/init          - Analyze codebase, generate CLAUDE.md
/memory        - Open CLAUDE.md for editing
/plan          - Enable Planning Mode
/effort        - Show/set effort level (low/medium/max)
/compact       - Summarize conversation, keep knowledge
/clear         - Fresh conversation (previous saved)
/rewind        - Jump to earlier conversation point (also: Escape twice)
/resume        - Resume previous cleared conversation
/hooks         - Manage hooks via UI
/install-github-app - Set up GitHub Actions integration
```

### 7.11 Custom Command Example (`.claude/commands/write_tests.md`)
```markdown
Write comprehensive tests for: $ARGUMENTS

Testing conventions:
* Use Vitest with React Testing Library
* Place test files in __tests__ directory (same folder as source)
* Name test files as [filename].test.ts(x)
* Use @/ prefix for imports

Coverage:
* Happy paths
* Edge cases
* Error states
```
Usage: `/write_tests the use-auth.ts file in the hooks directory`

### 7.12 Hook Configuration (`.claude/settings.local.json`)
```json
{
  "permissions": {
    "allow": ["mcp__playwright"],
    "deny": []
  },
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "Read",
        "hooks": [
          { "type": "command", "command": "node $PWD/hooks/read_hook.js" }
        ]
      }
    ],
    "PostToolUse": [
      {
        "matcher": "Write|Edit|MultiEdit",
        "hooks": [
          { "type": "command", "command": "node $PWD/hooks/edit_hook.js" }
        ]
      }
    ]
  }
}
```

### 7.13 Hook Script — Block .env File Access
```javascript
// hooks/read_hook.js
process.stdin.setEncoding("utf8");
let input = "";
process.stdin.on("data", (d) => (input += d));
process.stdin.on("end", () => {
  const toolArgs = JSON.parse(input);
  const readPath = toolArgs.tool_input?.file_path || "";
  
  if (readPath.includes(".env")) {
    console.error("You cannot read the .env file");  // Sent to Claude
    process.exit(2);  // Block the tool call
  }
  process.exit(0);  // Allow the tool call
});
```

### 7.14 Hook Input JSON Structure
```json
{
  "session_id": "2d6a1e4d-6...",
  "transcript_path": "/Users/sg/...",
  "hook_event_name": "PreToolUse",
  "tool_name": "Read",
  "tool_input": {
    "file_path": "/code/queries/.env"
  }
}
```

### 7.15 Debug Hook — Inspect All Input
```json
{
  "PostToolUse": [
    {
      "matcher": "*",
      "hooks": [
        { "type": "command", "command": "jq . > post-log.json" }
      ]
    }
  ]
}
```

### 7.16 MCP Sampling — Server Side
```python
from mcp.server.fastmcp import FastMCP, Context
from mcp.types import SamplingMessage, TextContent

mcp = FastMCP(name="Demo Server")

@mcp.tool()
async def summarize(text_to_summarize: str, ctx: Context):
    prompt = f"Please summarize the following text:\n{text_to_summarize}"
    
    result = await ctx.session.create_message(
        messages=[
            SamplingMessage(
                role="user",
                content=TextContent(type="text", text=prompt)
            )
        ],
        max_tokens=4000,
        system_prompt="You are a helpful research assistant",
    )
    
    if result.content.type == "text":
        return result.content.text
    else:
        raise ValueError("Sampling failed")

if __name__ == "__main__":
    mcp.run(transport="stdio")
```

### 7.17 MCP Sampling — Client Side
```python
from anthropic import AsyncAnthropic
from mcp import ClientSession, StdioServerParameters
from mcp.client.stdio import stdio_client
from mcp.client.session import RequestContext
from mcp.types import CreateMessageRequestParams, CreateMessageResult, TextContent, SamplingMessage

anthropic_client = AsyncAnthropic()
model = "claude-sonnet-4-0"

async def chat(input_messages: list[SamplingMessage], max_tokens=4000):
    messages = []
    for msg in input_messages:
        if msg.role == "user" and msg.content.type == "text":
            messages.append({"role": "user", "content": msg.content.text})
        elif msg.role == "assistant" and msg.content.type == "text":
            messages.append({"role": "assistant", "content": msg.content.text})
    
    response = await anthropic_client.messages.create(
        model=model, messages=messages, max_tokens=max_tokens
    )
    return "".join([p.text for p in response.content if p.type == "text"])

async def sampling_callback(context: RequestContext, params: CreateMessageRequestParams):
    text = await chat(params.messages)  # Client calls Claude on behalf of server
    return CreateMessageResult(
        role="assistant", model=model,
        content=TextContent(type="text", text=text)
    )

async def run():
    server_params = StdioServerParameters(command="uv", args=["run", "server.py"])
    async with stdio_client(server_params) as (read, write):
        async with ClientSession(read, write, sampling_callback=sampling_callback) as session:
            await session.initialize()
            result = await session.call_tool("summarize", {"text_to_summarize": "lots of text"})
            print(result.content)
```

### 7.18 MCP Roots — Server Implementation
```python
from pathlib import Path
from mcp.server.fastmcp import FastMCP, Context
from pydantic import Field

mcp = FastMCP("VidsMCP", log_level="ERROR")

async def is_path_allowed(requested_path: Path, ctx: Context) -> bool:
    roots_result = await ctx.session.list_roots()
    if not requested_path.exists():
        return False
    
    if requested_path.is_file():
        requested_path = requested_path.parent
    
    for root in roots_result.roots:
        root_path = file_url_to_path(root.uri)
        try:
            requested_path.relative_to(root_path)
            return True  # Path is within this root
        except ValueError:
            continue
    return False

@mcp.tool()
async def convert_video(
    input_path: str = Field(description="Path to the input MP4 file"),
    format: str = Field(description="Output format (e.g. 'mov')"),
    *, ctx: Context
):
    """Convert an MP4 video file to another format using ffmpeg"""
    input_file = VideoConverter.validate_input(input_path)
    
    if not await is_path_allowed(input_file, ctx):
        raise ValueError(f"Access to path is not allowed: {input_path}")
    
    return await VideoConverter.convert(input_path, format)
```

### 7.19 MCP Roots — Client with Root Paths
```python
from mcp import ClientSession, types
from mcp.types import Root
from pathlib import Path
from pydantic import FileUrl

class MCPClient:
    def __init__(self, command, args, roots=None):
        self._roots = self._create_roots(roots) if roots else []
    
    def _create_roots(self, root_paths: list[str]) -> list[Root]:
        roots = []
        for path in root_paths:
            p = Path(path).resolve()
            file_url = FileUrl(f"file://{p}")
            roots.append(Root(uri=file_url, name=p.name or "Root"))
        return roots
    
    async def _handle_list_roots(self, context) -> ListRootsResult:
        return ListRootsResult(roots=self._roots)  # Returns approved directories
    
    async def connect(self):
        self._session = await ClientSession(
            _stdio, _write,
            list_roots_callback=self._handle_list_roots if self._roots else None
        )
        await self._session.initialize()
```

### 7.20 MCP Notifications — Context Methods
```python
@mcp.tool()
async def research(topic: str, *, context: Context):
    await context.info("Starting research...")       # Logging notification
    await context.report_progress(20, 100)           # 20% progress

    sources = await do_research(topic)
    
    await context.info("Writing report...")
    await context.report_progress(70, 100)           # 70% progress
    
    results = await generate_report(sources)
    return results
```

### 7.21 Agent SDK Usage
```javascript
import { query } from "@anthropic-ai/claude-agent-sdk";

// Basic usage
for await (const message of query({ prompt: "List files in current directory" })) {
    console.log(JSON.stringify(message, null, 2));
}

// Restricted tools
for await (const message of query({
    prompt: "What's in the current directory?",
    options: { allowedTools: ["Read", "Glob"] }
})) {
    // Process messages
}
```

### 7.22 GitHub Actions — Claude Code Integration
```yaml
# .github/workflows/claude.yml (auto-generated by /install-github-app)
- name: Project Setup
  run: |
    npm run setup
    npm run dev:daemon

custom_instructions: |
  The project is already set up. Server running at localhost:3000.

mcp_config: |
  {
    "mcpServers": {
      "playwright": {
        "command": "npx",
        "args": ["@playwright/mcp@latest", "--allowed-origins", "localhost:3000"]
      }
    }
  }

# Must explicitly list all allowed tools in GitHub Actions
allowed_tools: "Bash(npm:*),mcp__playwright__browser_snapshot,mcp__playwright__browser_click"
```

---

## 8. Real-World Use Cases

### 8.1 Math Tutor Chatbot (System Prompts)
- **Problem:** Generic Claude answers math questions directly — students don't learn
- **Solution:** System prompt: "You are a patient math tutor. Do not directly answer. Guide step by step."
- **Result:** Claude asks guiding questions instead of giving answers
- **Impact:** Personalized educational AI that promotes critical thinking

### 8.2 AWS EventBridge Rule Generator (Structured Output)
- **Problem:** Claude wraps JSON in markdown with explanatory text; users can't copy-paste directly
- **Solution:** Prefill `"```json"` + stop sequence `"```"` → clean JSON only
- **Impact:** Web app users get immediately usable JSON without manual extraction

### 8.3 Real-Time Chat Application (Streaming)
- **Problem:** 10-30 second wait for complete Claude response creates poor UX
- **Solution:** Stream text chunks via `client.messages.stream()` + forward via WebSocket/SSE
- **Impact:** Text appears word-by-word like a real person is typing

### 8.4 TypeScript Type Checking Hook (Code Quality)
- **Problem:** Claude updates function signature but misses call sites → type errors
- **Solution:** PostToolUse hook runs `tsc --noEmit` after every file edit → feeds errors to Claude
- **Impact:** Automatically catches and fixes cross-file type inconsistencies

### 8.5 Query Duplication Prevention Hook (Code Quality)
- **Problem:** Claude creates duplicate database queries instead of reusing existing ones
- **Solution:** PostToolUse hook on `queries/` dir → launches separate Claude instance → reviews changes → provides feedback if duplicate found
- **Impact:** Cleaner codebase with enforced DRY principles

### 8.6 Research Tool with MCP Sampling (Public API Server)
- **Problem:** Public MCP server needs AI summarization but doesn't want to pay per user
- **Solution:** Sampling — server fetches Wikipedia articles, asks client to call Claude → gets summary
- **Impact:** Server functionality remains AI-powered; cost borne by each client, not the server operator

### 8.7 Video Converter with MCP Roots (Security)
- **Problem:** User asks "convert biking.mp4" without path; Claude needs to find it securely
- **Solution:** Roots restrict access to `~/Movies`; Claude uses `list_roots` + `read_dir` → finds file → converts
- **Impact:** User-friendly (no full paths needed) + secure (cannot access sensitive directories)

### 8.8 Playwright Browser Testing (MCP Integration)
- **Problem:** Claude can review code but can't see the actual visual output
- **Solution:** Playwright MCP server gives Claude browser control → navigate app → analyze UI → update prompts
- **Impact:** Claude can now make visually-informed decisions (warm gradients, unconventional layouts)

### 8.9 GitHub PR Review Automation
- **Problem:** Code reviews are time-consuming; pull requests sit unreviewed
- **Solution:** Claude Code GitHub Action automatically reviews every PR, analyzes impact, posts detailed report
- **Impact:** Instant automated code review on every pull request

### 8.10 Production Scaling with Stateless HTTP
- **Problem:** MCP server becomes popular; single instance can't handle thousands of clients
- **Solution:** `stateless_http=True` + load balancer → multiple server instances without session coordination
- **Impact:** Horizontally scalable MCP server at the cost of sampling/notifications

---

## 9. Interview Questions & Answers

### Q1: Why should Claude API calls never be made from client-side code?
**A:** The API requires a secret **API key** for authentication. Client-side JavaScript is visible to anyone inspecting browser network traffic. If exposed, anyone can extract the key and make unauthorized requests at your expense. All Claude API calls must go through a **server you control** that securely stores the API key.

---

### Q2: What happens inside Claude when it generates text? Walk through the 4 stages.
**A:**
1. **Tokenization:** Input text is broken into tokens (words, sub-words, punctuation, symbols)
2. **Embedding:** Each token is converted to a numerical vector (embedding) capturing all possible meanings
3. **Contextualization:** Embeddings are refined based on surrounding tokens (via attention mechanism) to resolve the appropriate meaning
4. **Generation:** Probability distribution is computed for next token; temperature controls sampling randomness; repeats until stop condition

---

### Q3: What is response prefilling and why is it useful?
**A:** Prefilling means adding an `assistant` message at the END of the messages list. Claude sees this and continues generating from that point instead of starting fresh. Key uses:
- **Structured output:** Prefill `"```json"` → Claude writes only JSON → stop sequence `"```"` captures it cleanly
- **Bias steering:** Prefill `"I think coffee is better"` → Claude argues that position
- Prefilled text is NOT repeated in the output; Claude picks up right after it

---

### Q4: What is the difference between PreToolUse and PostToolUse hooks?
**A:**
- **PreToolUse:** Runs BEFORE the tool executes. Can **BLOCK** the tool call by exiting with code 2. Any stderr output becomes the message Claude sees. Used for access control, validation.
- **PostToolUse:** Runs AFTER the tool executes. Cannot block (too late). Can provide additional feedback to Claude or trigger follow-up operations (formatting, testing, linting).

---

### Q5: Explain MCP Sampling and when you would use it.
**A:** Sampling allows an MCP server to delegate LLM calls to the connected client. Instead of the server having its own API key and calling Claude directly, it sends a `create_message` request to the client, which calls Claude and returns the result.

**When to use:** When building publicly accessible MCP servers. You don't want to pay for AI usage for every user who connects to your server. Sampling makes each client pay for their own AI usage while still benefiting from your server's functionality.

---

### Q6: How does MCP Roots improve security and user experience?
**A:**
- **UX:** Users don't need to provide full file paths; Claude discovers files within approved directories
- **Security:** Server can only access files within approved root directories; attempting outside roots returns an error
- **Important:** Roots are NOT automatically enforced by the MCP SDK — developers must implement `is_path_allowed()` manually by calling `ctx.session.list_roots()` and checking `path.relative_to(root_path)`

---

### Q7: Compare Stdio and StreamableHTTP transports for MCP.
**A:**

| Dimension | Stdio | StreamableHTTP |
|-----------|-------|---------------|
| Location | Same machine only | Client and server can be on different machines |
| Communication | stdin/stdout streams | HTTP with SSE for server→client |
| Bidirectional | ✅ Natural | ✅ Via dual SSE connections |
| Production scaling | ❌ Not scalable | ✅ With stateless_http mode |
| Session IDs | ❌ Not needed | ✅ Required (`mcp-session-id` header) |

Use stdio for local development; StreamableHTTP for remote/public MCP servers.

---

### Q8: What does `stateless_http=True` do and when would you use it?
**A:** It disables stateful HTTP mode, eliminating session IDs. This enables **horizontal scaling** behind a load balancer since requests from the same client can go to different server instances without coordination issues.

**Trade-offs when True:**
- ❌ No server-to-client requests
- ❌ No sampling (can't use Claude)
- ❌ No progress reports
- ❌ No resource subscriptions
- ✅ Supports multiple server instances + load balancing

Use when: you need horizontal scaling and your tools don't need AI capabilities or real-time notifications.

---

### Q9: What is the StreamableHTTP dual SSE connection model?
**A:** StreamableHTTP creates two separate SSE connections for each tool call:
1. **Primary SSE:** Open indefinitely after initialization. Used for server-initiated requests (sampling requests, etc.) and progress notifications
2. **Tool-Specific SSE:** Created when client sends a POST tool call. Used for messages related to that specific tool call. Closes automatically when the `CallToolResult` is sent

This enables full bidirectional MCP communication over standard HTTP.

---

### Q10: How does Claude Code use hooks for TypeScript type checking?
**A:** A PostToolUse hook is configured to trigger after Write/Edit/MultiEdit tool calls:
1. Hook runs `tsc --noEmit` on the modified file
2. If type errors are found, they're captured and fed back to Claude
3. Claude immediately sees the errors and knows to fix them in other files
4. This solves the common problem of Claude updating a function signature but missing call sites

---

### Q11: What is the CLAUDE.md file and what are its three variants?
**A:** CLAUDE.md is a markdown file that gets included in **every request** to Claude Code — acting as a persistent system prompt for your project. It guides Claude through architecture, important commands, and coding conventions.

Three variants:
1. `CLAUDE.md` — Project root, committed to git, shared with team
2. `CLAUDE.local.md` — Project root, NOT committed, personal customizations
3. `~/.claude/CLAUDE.md` — Home directory, applies to ALL projects on your machine

---

### Q12: How do custom commands work in Claude Code?
**A:** Create a markdown file in `.claude/commands/` directory:
- Filename becomes the command: `audit.md` → `/audit`
- Use `$ARGUMENTS` placeholder to accept user input: `/write_tests the auth.ts file`
- Claude Code picks up new commands automatically without restart
- Perfect for automating project-specific workflows (testing, deployment, linting)

---

### Q13: What is the MCP 3-message handshake and why is it required?
**A:** Every MCP connection must start with this sequence before any other requests:
1. **Initialize Request** — Client sends first, with capabilities
2. **Initialize Result** — Server responds with its capabilities (and `mcp-session-id` in StreamableHTTP)
3. **Initialized Notification** — Client confirms; no response expected

Only after this handshake can tool calls, prompt listings, or resource reads occur. Skipping it results in errors from the server.

---

### Q14: Explain the difference between MCP Request-Result messages and Notifications.
**A:**
- **Request-Result pairs:** Always two-way. Client sends request, server must send back a result. Examples: Call Tool Request → Result, Initialize Request → Result
- **Notifications:** One-way messages; no response expected. Examples: Progress Notification (server→client), Logging Message (server→client), Initialized Notification (client→server after handshake)

Notifications are critical for UX — they tell users what's happening during long operations.

---

### Q15: How does the Agent SDK differ from the Claude Code CLI?
**A:**
- **Claude Code CLI:** Interactive terminal assistant, used directly by developers
- **Agent SDK** (`@anthropic-ai/claude-agent-sdk`): Library for running Claude Code programmatically from applications and scripts
  - Exposes same agent loop (file reading, editing, tool use)
  - Returns stream of JSON messages
  - Supports: custom system prompts, MCP servers, hooks, subagents, session resumption
  - Use for automation pipelines, hook implementations that spawn sub-agents, integration into custom tools

---

## 10. Certification Questions

### CQ1: What API call sequence is required to establish an MCP connection with StreamableHTTP?
- A) Call a tool immediately after connecting
- **B) Initialize Request → Initialize Result → Initialized Notification** ✅
- C) Initialized Notification → Initialize Request → Initialize Result
- D) No handshake required for HTTP connections

**Explanation:** Every MCP connection (regardless of transport) must start with this 3-message handshake before any other requests.

---

### CQ2: A PreToolUse hook script exits with code 2. What happens?
- A) The tool runs normally
- B) The hook script re-runs
- **C) The tool call is blocked; stderr message sent to Claude** ✅
- D) The session is terminated

**Explanation:** Exit code 0 = allow, exit code 2 = block (PreToolUse only). Any stderr output becomes Claude's error message.

---

### CQ3: When should you use MCP Sampling?
- A) When you need to embed files in an MCP server response
- B) When the MCP server needs to directly call Claude with its own API key
- **C) When a public MCP server needs AI capabilities without paying per-user AI costs** ✅
- D) When you need real-time progress notifications

**Explanation:** Sampling delegates the LLM call from server to client, shifting AI cost responsibility to each individual client.

---

### CQ4: What does setting `stateless_http=True` on an MCP server enable?
- A) Faster tool responses
- B) Real-time progress notifications to clients
- **C) Horizontal scaling with load balancers** ✅
- D) Server-initiated sampling requests

**Explanation:** Stateless mode eliminates sessions, allowing load balancers to route requests to any server instance without coordination. The trade-off is losing server→client communication.

---

### CQ5: Which of these is NOT a valid CLAUDE.md file location?
- A) `CLAUDE.md` in project root (committed)
- B) `CLAUDE.local.md` in project root
- C) `~/.claude/CLAUDE.md` in home directory
- **D) `~/.anthropic/CLAUDE.md` in home directory** ✅

**Explanation:** The three valid locations are project root CLAUDE.md, CLAUDE.local.md, and `~/.claude/CLAUDE.md`.

---

### CQ6: What is the purpose of the `$ARGUMENTS` placeholder in custom Claude Code commands?
- A) References the current file being edited
- **B) Gets replaced with user-provided input when the command is invoked** ✅
- C) Passes environment variables to Claude
- D) Specifies which files to include in the context

---

### CQ7: In StreamableHTTP, what happens to the Primary SSE connection after a tool call completes?
- A) It closes immediately
- B) It sends the tool result then closes
- **C) It stays open indefinitely for server-initiated requests** ✅
- D) It reconnects for each new request

**Explanation:** The primary SSE stays open indefinitely and is used for server-initiated requests (sampling, notifications). A separate tool-specific SSE connection closes after each tool call result is sent.

---

### CQ8: Which Claude Code command summarizes the conversation while preserving key knowledge?
- A) `/clear`
- **B) `/compact`** ✅
- C) `/rewind`
- D) `/init`

**Explanation:** `/compact` summarizes history while keeping important context. `/clear` starts completely fresh (previous saved for `/resume`). `/rewind` jumps to an earlier point.

---

### CQ9: What is the minimum required configuration for a streaming API request?
- A) model, max_tokens, messages, stream=True, temperature
- **B) model, max_tokens, messages (stream=True or use .stream() context manager)** ✅
- C) model, max_tokens, messages, system prompt, stream=True
- D) model, messages, stream=True

**Explanation:** Only model, max_tokens, and messages are required. system, temperature, and stop_sequences are optional.

---

### CQ10: Why must `is_path_allowed()` be implemented manually when using MCP Roots?
- **A) The MCP SDK does not automatically enforce root restrictions** ✅
- B) Root restrictions are server-side only
- C) The SDK enforces roots but only for file writes, not reads
- D) Root enforcement requires a paid MCP tier

**Explanation:** The MCP specification defines roots as a convention, but enforcement is the developer's responsibility. You must call `list_roots()` and validate paths yourself.

---

## 11. Revision Notes

### Quick Reference — Hook Types Summary

| Hook | Trigger | Can Block? | Key Use Case |
|------|---------|-----------|-------------|
| `PreToolUse` | Before tool | ✅ Exit 2 | Access control, validation |
| `PostToolUse` | After tool | ❌ Feedback only | Formatting, testing, quality checks |
| `Notification` | Permission needed / 60s idle | — | Custom notifications |
| `Stop` | Claude finishes responding | — | Logging, cleanup |
| `SubagentStop` | Subagent (Task) finishes | — | Subagent workflow tracking |
| `PreCompact` | Before compact | — | Pre-processing |
| `UserPromptSubmit` | User sends a prompt | — | Input validation/logging |
| `SessionStart` | Session start/resume | — | Initialization |
| `SessionEnd` | Session ends | — | Cleanup/persistence |

---

### Quick Reference — Hook Input Data Fields

```json
// PreToolUse / PostToolUse hook input
{
  "session_id": "...",
  "transcript_path": "...",
  "hook_event_name": "PreToolUse | PostToolUse",
  "tool_name": "Read | Write | Edit | Bash | ...",
  "tool_input": { "file_path": "..." },    // Varies by tool
  "tool_response": { ... }                 // Only in PostToolUse
}

// Stop hook input  
{
  "session_id": "...",
  "transcript_path": "...",
  "hook_event_name": "Stop",
  "stop_hook_active": false
}
```

**Tool input shapes differ by tool:**
- `Read` → `{"file_path": "..."}`
- `Grep` → `{"pattern": "...", "path": "..."}`
- `Bash` → `{"command": "..."}`
- `TodoWrite` → `{"todos": [...]}`

---

### Quick Reference — MCP Message Types

| Message | Direction | Response Required? |
|---------|-----------|-------------------|
| Initialize Request | Client → Server | ✅ Yes |
| Initialize Result | Server → Client | ❌ No |
| Initialized Notification | Client → Server | ❌ No |
| Call Tool Request | Client → Server | ✅ Yes |
| Call Tool Result | Server → Client | ❌ No |
| List Prompts Request | Client → Server | ✅ Yes |
| Read Resource Request | Client → Server | ✅ Yes |
| Progress Notification | Server → Client | ❌ No |
| Logging Message | Server → Client | ❌ No |
| Create Message Request (Sampling) | Server → Client | ✅ Yes |
| List Roots Request | Server → Client | ✅ Yes |

---

### Quick Reference — `@` Syntax in CLAUDE.md
```markdown
# How does the auth system work? @auth   ← File mention in chat

# In CLAUDE.md:
The schema is in @prisma/schema.prisma. Reference for all data questions.
@AGENTS.md                                ← Import another config file
```

---

## 12. One-Page Summary

```
=========================================================================
     BUILDING WITH CLAUDE API — CERTIFICATION CHEAT SHEET
=========================================================================

5-STEP REQUEST LIFECYCLE:
  Client → Your Server → Anthropic API → Claude (Tokenize→Embed→Context→Generate)
  → Response (text + usage + stop_reason) → Server → Client

API ESSENTIALS:
  Required: model, max_tokens, messages
  Optional: system, temperature, stop_sequences, stream=True
  NEVER expose API key in client-side code!
  Claude API does NOT accept system=None → conditionally include it

TEMPERATURE GUIDE:
  0.0-0.3 = Factual/Code/Extraction
  0.4-0.7 = Summarization/Education
  0.8-1.0 = Creative/Brainstorming

STRUCTURED OUTPUT:
  Prefill "```json" + stop_sequence=["```"] → clean JSON only

STREAMING:
  client.messages.stream() → stream.text_stream → stream.get_final_message()

CLAUDE CODE:
  Install: curl .../install.sh | bash  OR  irm .../install.ps1 | iex
  CLAUDE.md = persistent project context (3 locations)
  /init → create CLAUDE.md | /plan → Planning Mode | /effort → reasoning depth
  /compact → summarize | /clear → fresh | /rewind → go back

HOOKS:
  PreToolUse: Exit 0=allow | Exit 2=BLOCK (stderr → Claude message)
  PostToolUse: Cannot block, only feedback
  Config: ~/.claude/settings.json (global) | .claude/settings.json (project)
  Security: Absolute paths | sanitize inputs | block ".." in paths

MCP SAMPLING:
  Server → ctx.session.create_message() → Client calls Claude → Returns result
  Use for: public servers where you don't want to pay per-user AI costs

MCP ROOTS:
  Purpose: Security-bounded file access + user-friendly (no full paths)
  Not auto-enforced! → implement is_path_allowed() with list_roots()

MCP TRANSPORTS:
  Stdio: Same machine only | Simple | Best for dev/testing
  StreamableHTTP: Remote servers | SSE for bidirectional | Session IDs required
  stateless_http=True: Horizontal scaling; loses server→client, sampling, progress
  json_response=True: No streaming; final result only

STREAMABLEHTTP CONNECTIONS:
  Primary SSE: Open indefinitely | Server→Client requests
  Tool-Specific SSE: Per tool call | Logging+Result | Closes after result

MCP HANDSHAKE:
  Initialize Request → Initialize Result (+ mcp-session-id) → Initialized Notification

AGENT SDK: @anthropic-ai/claude-agent-sdk
  query({ prompt, options: { allowedTools: [...] } })

GITHUB INTEGRATION:
  /install-github-app → @claude mentions + auto PR review
  Must explicitly list allowed_tools in GitHub Actions YAML
=========================================================================
```

---

## 13. Top 20 Key Takeaways

1. **Never expose API keys in client-side code** — always route through a server you control
2. **Claude is stateless** — send complete conversation history with every API call
3. **Text generation = 4 stages**: Tokenize → Embed → Contextualize → Generate (with temperature sampling)
4. **max_tokens is a ceiling, not a target** — Claude stops naturally before it if appropriate
5. **Temperature 0 = deterministic, Temperature 1 = creative** — match to your use case
6. **Prefill + Stop Sequences = structured output control** — get clean JSON/code without explanatory text
7. **Streaming splits: text chunks (UX) + get_final_message() (storage/logic)** — use both
8. **Claude Code's power = Tool Use** — language models need tools to interact with the real world
9. **CLAUDE.md = persistent project system prompt** — 3 variants for different scoping needs
10. **PreToolUse exit 2 BLOCKS tools; PostToolUse cannot block** — know the difference
11. **Hook security = absolute paths + sanitize inputs + block path traversal** — always follow the 5 rules
12. **MCP Sampling = server delegates AI to client** — ideal for public servers avoiding per-user costs
13. **MCP Roots are NOT automatically enforced** — you must implement `is_path_allowed()` manually
14. **Stdio = same machine only; StreamableHTTP = remote possible** — choose based on deployment needs
15. **StreamableHTTP uses DUAL SSE connections** — Primary (permanent) + Tool-Specific (per-call)
16. **stateless_http=True enables horizontal scaling** but eliminates sampling, notifications, and sessions
17. **MCP 3-message handshake is mandatory** — Initialize Request → Result → Notification
18. **Session ID must be sent in all requests** after initialization with StreamableHTTP
19. **Custom Claude Code commands** use markdown files in `.claude/commands/` + `$ARGUMENTS` placeholder
20. **Agent SDK** (`@anthropic-ai/claude-agent-sdk`) enables programmatic Claude Code — same capabilities as CLI

---

> 📁 **Source Files Analyzed:**
> - `Building with the Claude API.txt` (626 lines) — API fundamentals, requests, system prompts, temperature, streaming, structured output
> - `Claude Code in Action.txt` (797 lines) — Agent loop, context management, hooks, GitHub integration, Agent SDK
> - `Model Context Protocol- Advanced Topics.txt` (575 lines) — Sampling, Notifications, Roots, Message Protocol, Transports, StreamableHTTP
> - `sampling/client.py` + `sampling/server.py` — Complete MCP sampling implementation
> - `roots/mcp_server.py` + `roots/mcp_client.py` — Complete MCP roots implementation
> - **13 images analyzed:** img1–7 (Claude Code in Action) + img2–7 (MCP Advanced Topics)

*Created: June 2026 — Claude Certification Preparation Series*
*File: `E:\Teja_Interview_preparation\Claude_Preparation\Claude_Architect_Preparation\Building_with_Claude_API.md`*
