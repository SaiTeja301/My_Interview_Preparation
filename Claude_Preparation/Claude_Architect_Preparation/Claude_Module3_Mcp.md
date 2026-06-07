# 🔌 MODULE 3 — MODEL CONTEXT PROTOCOL (MCP)
### Certification & Interview Preparation Notes
### Source: `E:\Teja_Interview_preparation\Claude_Preparation\MCP-Model Context Protocol\`

> [!IMPORTANT]
> This file covers **Module 3 ONLY** — Model Context Protocol (MCP).
> - Module 1 (AI Fluency): `Claude_Module1_AI_Fluency.md`
> - Module 2 (Claude 101): `Claude_Module2_Preparation_Notes.md`
> - Module 3 (MCP): `Claude_Module3_Mcp.md`

---

## 📚 TABLE OF CONTENTS

1. [What is MCP?](#1-what-is-mcp)
2. [The Problem MCP Solves](#2-the-problem-mcp-solves)
3. [MCP Architecture](#3-mcp-architecture)
4. [MCP Layers](#4-mcp-layers)
5. [MCP Primitives — Core Building Blocks](#5-mcp-primitives--core-building-blocks)
6. [Client Primitives (Server → Client)](#6-client-primitives-server--client)
7. [MCP Lifecycle — Step by Step](#7-mcp-lifecycle--step-by-step)
8. [Building an MCP Server (Python SDK)](#8-building-an-mcp-server-python-sdk)
9. [Testing with MCP Inspector](#9-testing-with-mcp-inspector)
10. [Building an MCP Client (Python SDK)](#10-building-an-mcp-client-python-sdk)
11. [Resources in MCP](#11-resources-in-mcp)
12. [Prompts in MCP](#12-prompts-in-mcp)
13. [Transport Mechanisms](#13-transport-mechanisms)
14. [Connecting to Claude Desktop](#14-connecting-to-claude-desktop)
15. [Remote MCP Servers](#15-remote-mcp-servers)
16. [MCP Versioning](#16-mcp-versioning)
17. [uv — Python Package Manager](#17-uv--python-package-manager)
18. [Key Definitions Glossary](#18-key-definitions-glossary)
19. [Interview Questions & Answers](#19-interview-questions--answers)
20. [Certification Questions with Answers](#20-certification-questions-with-answers)
21. [One-Page Quick Revision Summary](#21-one-page-quick-revision-summary)
22. [Top 20 MCP Key Takeaways](#22-top-20-mcp-key-takeaways)

---

## 1. WHAT IS MCP?

**MCP (Model Context Protocol)** is an **open-source standard** (protocol) for connecting AI applications to external systems.

Using MCP, AI applications like Claude or ChatGPT can connect to:
- **Data sources** — local files, databases
- **Tools** — search engines, calculators, API services
- **Workflows** — specialized pre-built prompts

> **The Core Analogy ★:** MCP is like a **USB-C port for AI applications**.
> Just as USB-C provides a standardized way to connect electronic devices regardless of manufacturer, MCP provides a standardized way to connect AI applications to external systems.

### Who Benefits from MCP?

| Role | Benefit |
|------|---------|
| **Developers** | Reduced development time; no need to write all integration code from scratch |
| **AI Applications / Agents** | Access to an ecosystem of data sources, tools, and apps |
| **End-Users** | More capable AI applications that can access data and take actions on their behalf |

### What MCP Can Enable

- Agents that access your Google Calendar and Notion as a personalized AI assistant
- Claude Code that generates an entire web app using a Figma design
- Enterprise chatbots that connect to multiple databases and let users analyze data via chat
- AI models that create 3D designs in Blender and send them to a 3D printer

### Broad Ecosystem Support

MCP is an open protocol supported by:
- **AI Assistants:** Claude, ChatGPT
- **Dev Tools:** Visual Studio Code, Cursor, MCPJam
- Build once → integrate everywhere

---

## 2. THE PROBLEM MCP SOLVES

### Before MCP — The Integration Burden

Imagine building a chat interface where users can ask Claude about GitHub data (e.g., "What open pull requests are there?"):

- GitHub has **massive functionality** — repos, PRs, issues, projects, and more
- Without MCP, you must write **all the tool schemas and functions** yourself
- That means writing, testing, and maintaining all that integration code

```
❌ WITHOUT MCP:
Your App → [You write 100s of tool schemas] → GitHub API
                                             → Slack API
                                             → Jira API
                                             → Salesforce API
```

### After MCP — Shift the Burden to Dedicated Servers

MCP moves tool definitions and execution from YOUR server to specialized **MCP Servers**.

```
✅ WITH MCP:
Your App → MCP Client → GitHub MCP Server → GitHub API
                      → Slack MCP Server  → Slack API
                      → Jira MCP Server   → Jira API
```

The MCP Server wraps all the functionality for a service and exposes it as a **standardized set of tools**. Your application connects to this MCP server instead of implementing everything from scratch.

### MCP vs. Direct Tool Use

| Aspect | MCP | Direct Tool Use |
|--------|-----|----------------|
| Tool Definitions | Provided by MCP Server (someone else writes them) | YOU write all JSON schemas |
| Maintenance | MCP Server author maintains | You maintain |
| Integration | Standardized across all tools | Custom per tool |
| Reusability | Any MCP client can use the same server | Specific to your app |

> **Common Misconception:** "MCP is just tool use." — **No.** MCP and tool use are complementary. Tool use is *how* Claude calls tools. MCP is *who* defines and implements the tools.

---

## 3. MCP ARCHITECTURE

### Three Key Participants ★

| Participant | Role | Example |
|------------|------|---------|
| **MCP Host** | The AI application that coordinates and manages one or multiple MCP clients | Claude Desktop, VS Code, Claude.ai |
| **MCP Client** | A component that maintains a connection to ONE MCP server and obtains context for the Host | A session object inside your application code |
| **MCP Server** | A program that exposes tools, resources, and prompts to MCP clients | GitHub MCP server, Filesystem server |

> **Key Rule:** One MCP Host → Creates **multiple** MCP Clients → Each Client connects to **one** MCP Server

### Architecture Diagram

```
┌─────────────────────────────────────────┐
│           MCP Host (AI Application)     │
│  e.g., Claude Desktop / VS Code         │
│                                         │
│  ┌──────────────┐  ┌──────────────┐     │
│  │  MCP Client 1│  │  MCP Client 2│ ... │
│  └──────┬───────┘  └──────┬───────┘     │
└─────────│─────────────────│─────────────┘
          │ dedicated        │ dedicated
          │ connection       │ connection
          ▼                  ▼
┌──────────────────┐  ┌──────────────────┐
│  MCP Server A    │  │  MCP Server B    │
│  (Local/STDIO)   │  │  (Remote/HTTP)   │
│  e.g. Filesystem │  │  e.g. Sentry     │
└────────┬─────────┘  └────────┬─────────┘
         │                      │
         ▼                      ▼
   Local File System        Sentry API
```

### Local vs. Remote MCP Servers

| Type | Transport | Example |
|------|-----------|---------|
| **Local MCP Server** | STDIO | Filesystem server launched by Claude Desktop on same machine |
| **Remote MCP Server** | Streamable HTTP | Official Sentry MCP server on Sentry's platform |

> **Note:** "MCP server" refers to the **program** that serves context data, regardless of where it runs.

---

## 4. MCP LAYERS

MCP consists of **two layers**:

### Layer 1: Data Layer (Inner)

Defines the **JSON-RPC 2.0 based protocol** for client-server communication.

| Sub-component | Purpose |
|--------------|---------|
| **Lifecycle Management** | Connection initialization, capability negotiation, connection termination |
| **Server Features** | Tools (AI actions), Resources (context data), Prompts (interaction templates) |
| **Client Features** | Sampling, Elicitation, Logging |
| **Utility Features** | Notifications (real-time updates), Progress tracking for long-running operations |

### Layer 2: Transport Layer (Outer)

Manages communication channels and authentication between clients and servers.

Handles:
- Connection establishment
- Message framing
- Secure communication

---

## 5. MCP PRIMITIVES — CORE BUILDING BLOCKS

> [!IMPORTANT]
> MCP Primitives are the **most important concept** in MCP. They define what clients and servers can offer each other.

### Three Core Server Primitives ★★★

| Primitive | Definition | Who Controls | Protocol Methods | Example |
|-----------|-----------|-------------|-----------------|---------|
| **Tools** | Executable functions the AI model can invoke to perform actions | **Model** (AI decides when to call) | `tools/list`, `tools/call` | `search_flights()`, `send_email()`, `create_calendar_event()` |
| **Resources** | Read-only data sources providing contextual information | **Application** (app retrieves and presents) | `resources/list`, `resources/read` | File contents, DB records, calendar data |
| **Prompts** | Reusable parameterized templates structuring AI interactions | **User** (explicit invocation required) | `prompts/list`, `prompts/get` | `/format doc_id`, `/plan-vacation` |

> **Memory Aid:**
> - **Tools** = AI **DOES** things (actions)
> - **Resources** = App **READS** data (context)
> - **Prompts** = User **SELECTS** templates (workflows)

---

### TOOLS — Deep Dive

Tools enable AI models to perform actions. Each tool defines a specific operation with **typed inputs and outputs**.

**How Tools Work:**
- Schema-defined using **JSON Schema** for validation
- Each tool performs a **single operation** with clearly defined inputs and outputs
- Tools may require **user consent** before execution (for trust and safety)

**Tool Protocol Operations:**

| Method | Purpose | Returns |
|--------|---------|---------|
| `tools/list` | Discover available tools | Array of tool definitions with schemas |
| `tools/call` | Execute a specific tool | Tool execution result |

**Example Tool Definition:**

```json
{
  "name": "searchFlights",
  "description": "Search for available flights",
  "inputSchema": {
    "type": "object",
    "properties": {
      "origin": { "type": "string", "description": "Departure city" },
      "destination": { "type": "string", "description": "Arrival city" },
      "date": { "type": "string", "format": "date", "description": "Travel date" }
    },
    "required": ["origin", "destination", "date"]
  }
}
```

**User Control Mechanisms for Tools:**
- Displaying available tools in UI; users can toggle them per interaction
- Approval dialogs for individual tool executions
- Permission settings for pre-approving safe operations
- Activity logs showing all tool executions with results

---

### RESOURCES — Deep Dive

Resources provide **structured access to information** that the AI application retrieves and provides to models as context.

**Each resource has:**
- A unique **URI** (e.g., `file:///path/to/document.md`)
- A declared **MIME type** for appropriate content handling

**Two Resource Discovery Patterns:**

| Type | URI Pattern | Description |
|------|-------------|-------------|
| **Direct Resources** | Static URI (e.g., `calendar://events/2024`) | Fixed URIs pointing to specific data; no parameters |
| **Templated Resources** | Dynamic URI (e.g., `travel://activities/{city}/{category}`) | Parameterized URIs; parameters parsed and passed as function args |

**Resource Protocol Operations:**

| Method | Purpose |
|--------|---------|
| `resources/list` | List available direct resources |
| `resources/templates/list` | Discover resource templates |
| `resources/read` | Retrieve resource contents |
| `resources/subscribe` | Monitor resource changes |

**Parameter Completion Example:**
- Typing "Par" for `weather://forecast/{city}` → suggests "Paris" or "Park City"
- Typing "JFK" for `flights://search/{airport}` → suggests "JFK - John F. Kennedy International"

---

### PROMPTS — Deep Dive

Prompts provide **reusable templates** that are user-controlled and require explicit invocation.

**Key Characteristics:**
- **Parameterized:** Accept arguments for flexible use
- **User-controlled:** Unlike tools (model decides), prompts require explicit user invocation
- **Context-aware:** Can reference available resources and tools to create comprehensive workflows
- Support **parameter completion** to help users discover valid argument values

**Example Prompt Definition:**

```json
{
  "name": "plan-vacation",
  "title": "Plan a vacation",
  "description": "Guide through vacation planning process",
  "arguments": [
    { "name": "destination", "type": "string", "required": true },
    { "name": "duration", "type": "number", "description": "days" },
    { "name": "budget", "type": "number", "required": false },
    { "name": "interests", "type": "array", "items": { "type": "string" } }
  ]
}
```

**Common UI Patterns for Prompts:**
- Slash commands: `/plan-vacation`
- Command palettes (searchable access)
- Dedicated UI buttons for frequently used prompts
- Context menus suggesting relevant prompts

---

## 6. CLIENT PRIMITIVES (SERVER → CLIENT)

These are features clients expose that servers can use to build richer interactions.

| Feature | What It Enables | Use Case |
|---------|----------------|----------|
| **Sampling** | Servers request LLM completions from the client's AI application — without embedding an LLM themselves | Travel server asks Claude to analyze 47 flight options and pick the best |
| **Elicitation** | Servers request specific information from users during interactions | Booking server asks for seat preference, room type, travel insurance choice |
| **Roots** | Clients specify which directories servers should focus on | Travel workspace root limits server to relevant directories only |
| **Logging** | Servers send log messages to clients for debugging/monitoring | Debug messages from MCP server appear in client's log console |

### Sampling — Deep Dive ★

Sampling allows servers to request language model completions through the client.

**Sampling Flow:**
```
Server ──sampling/createMessage──► Client
                                     │── Present request for approval ──► User
                                     │◄── Review and approve/modify ────── User
                                     │── Forward approved request ─────► LLM
                                     │◄── Return generation ───────────── LLM
                                     │── Present response for approval ─► User
                                     │◄── Review and approve/modify ────── User
Server ◄──Return approved response ──┘
```

**Why Sampling Matters:**
- Server stays **model-independent** — doesn't need its own LLM SDK
- Puts **client in complete control** of user permissions and security
- Users maintain oversight via human-in-the-loop checkpoints

### Elicitation — Deep Dive ★

Elicitation enables servers to dynamically request information from users instead of requiring all info upfront.

**Elicitation Flow:**
```
Server ──elicitation/create──► Client ──Present UI──► User
Server ◄──Return user response── Client ◄──Provide info── User
Server: Continue processing with new information
```

**Elicitation Example (Travel Booking):**
```json
{
  "method": "elicitation/create",
  "params": {
    "message": "Please confirm your Barcelona vacation booking details:",
    "schema": {
      "type": "object",
      "properties": {
        "confirmBooking": { "type": "boolean", "description": "Confirm (Flights + Hotel = $3,000)" },
        "seatPreference": { "type": "string", "enum": ["window", "aisle", "no preference"] },
        "roomType": { "type": "string", "enum": ["sea view", "city view", "garden view"] },
        "travelInsurance": { "type": "boolean", "default": false }
      },
      "required": ["confirmBooking"]
    }
  }
}
```

---

## 7. MCP LIFECYCLE — STEP BY STEP

MCP is a **stateful protocol** requiring lifecycle management.

### Complete Initialization Sequence ★

```
Step 1: Client ──initialize──► Server
         (protocolVersion, capabilities, clientInfo)

Step 2: Server ──response──► Client
         (serverInfo, server capabilities — tools, resources, prompts)

Step 3: Client ──notifications/initialized──► Server
         (signals client is ready to proceed)

Step 4: Client ──tools/list──► Server
Step 5: Server ──[tool definitions array]──► Client

Step 6: Claude processes user query + available tools
Step 7: Claude decides to use a tool
Step 8: Client ──tools/call (tool_name, arguments)──► Server
Step 9: Server executes tool → calls External API
Step 10: Server ──CallToolResult──► Client
Step 11: Claude receives tool result → formulates final response
Step 12: User receives answer
```

### Initialize Request Example (JSON-RPC 2.0)

```json
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "initialize",
  "params": {
    "protocolVersion": "2025-06-18",
    "capabilities": {
      "elicitation": {}
    },
    "clientInfo": {
      "name": "example-client",
      "version": "1.0.0"
    }
  }
}
```

**Capability Negotiation Explained:**
- `"protocolVersion"` — both sides agree on a compatible version; terminate if not found
- `"capabilities"` — each party declares supported features and primitives
- `"clientInfo"` / `"serverInfo"` — identification and versioning for debugging

### Tool Discovery

```json
// Request
{ "jsonrpc": "2.0", "id": 2, "method": "tools/list" }
```

### Tool Execution

```json
// Request
{
  "jsonrpc": "2.0",
  "id": 3,
  "method": "tools/call",
  "params": {
    "name": "weather_current",
    "arguments": {
      "location": "San Francisco",
      "units": "imperial"
    }
  }
}
```

### Real-Time Notifications

Notifications are JSON-RPC messages with **no `id` field** (no response expected):

```json
// Server notifies client that tool list changed
{ "jsonrpc": "2.0", "method": "notifications/tools/list_changed" }
```

**Client Response to Notification:** Re-sends `tools/list` to refresh its tool registry.

**Why Notifications Matter:**
- Tools may come and go based on server state, user permissions, external dependencies
- Clients don't need to **poll** for changes — they're notified
- Ensures clients always have accurate information about server capabilities

---

## 8. BUILDING AN MCP SERVER (PYTHON SDK)

### Setup with uv

```bash
# Install uv (Windows)
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"

# Create project
uv init my-mcp-server
cd my-mcp-server

# Install MCP SDK
uv add "mcp[cli]"

# Create server file
# (create mcp_server.py)
```

### FastMCP — The Python SDK ★

The **FastMCP** class is the core of the Python SDK. It uses Python decorators and type hints to automatically generate proper tool/resource/prompt definitions — **no manual JSON schema writing required**.

```python
from mcp.server.fastmcp import FastMCP
from pydantic import Field

# Initialize server — give it a name
mcp = FastMCP("DocumentMCP", log_level="ERROR")
```

---

### Defining TOOLS with Decorators

```python
# In-memory document store
docs = {
    "deposition.md": "This deposition covers the testimony of Angela Smith, P.E.",
    "report.pdf": "The report details the state of a 20m condenser tower.",
    "financials.docx": "These financials outline the project's budget and expenditures",
    "outlook.pdf": "This document presents the projected future performance of the system",
    "plan.md": "The plan outlines the steps for the project's implementation.",
    "spec.txt": "These specifications define the technical requirements for the equipment"
}

# TOOL 1: Read document
@mcp.tool(
    name="read_doc_contents",
    description="Read the contents of a document and return it as a string."
)
def read_document(
    doc_id: str = Field(description="Id of the document to read")
):
    if doc_id not in docs:
        raise ValueError(f"Doc with id {doc_id} not found")
    return docs[doc_id]

# TOOL 2: Edit document
@mcp.tool(
    name="edit_document",
    description="Edit a document by replacing a string in the content."
)
def edit_document(
    doc_id: str = Field(description="Id of the document to edit"),
    old_str: str = Field(description="Text to replace. Must match exactly, including whitespace."),
    new_str: str = Field(description="New text to insert in place of the old text.")
):
    if doc_id not in docs:
        raise ValueError(f"Doc with id {doc_id} not found")
    docs[doc_id] = docs[doc_id].replace(old_str, new_str)
```

**Key Benefits of the SDK Decorator Approach:**
- No manual JSON schema writing
- Python type hints → automatic validation
- Field descriptions → Claude understands tool usage
- Error handling integrates with Python exceptions
- Tool registration happens automatically

---

### Defining RESOURCES with Decorators

**Direct Resource (static URI):**

```python
@mcp.resource(
    "docs://documents",
    mime_type="application/json"
)
def list_docs() -> list[str]:
    return list(docs.keys())
```

**Templated Resource (dynamic URI with parameters):**

```python
@mcp.resource(
    "docs://documents/{doc_id}",
    mime_type="text/plain"
)
def fetch_doc(doc_id: str) -> str:
    if doc_id not in docs:
        raise ValueError(f"Doc with id {doc_id} not found")
    return docs[doc_id]
```

**MIME Types to Know:**
- `"application/json"` → structured data
- `"text/plain"` → plain text
- `"application/pdf"` → binary files

> The SDK **automatically serializes** return values. Just return your data structure — no manual JSON conversion needed.

---

### Defining PROMPTS with Decorators

```python
from mcp import types as base

@mcp.prompt(
    name="format",
    description="Rewrites the contents of the document in Markdown format."
)
def format_document(
    doc_id: str = Field(description="Id of the document to format")
) -> list:
    prompt = f"""
Your goal is to reformat a document to be written with markdown syntax.

The id of the document you need to reformat is:
<document_id>
{doc_id}
</document_id>

Add in headers, bullet points, tables, etc as necessary. Feel free to add structure.
Use the 'edit_document' tool to edit the document after reformatting.
"""
    return [base.UserMessage(prompt)]
```

**Why Use Prompts?**
- Users can ask Claude directly, but they'll get **much better results** with carefully crafted, tested prompt templates
- As MCP server author, you encode **domain expertise** into prompts
- **Consistency** — reliable results across different users
- **Reusability** — multiple client apps use the same prompts
- **Maintenance** — update in one place to improve all clients

---

### Complete Minimal Weather MCP Server (FastMCP)

```python
from typing import Any
import httpx
from mcp.server.fastmcp import FastMCP

mcp = FastMCP("weather")

NWS_API_BASE = "https://api.weather.gov"
USER_AGENT = "weather-app/1.0"

async def make_nws_request(url: str) -> dict[str, Any] | None:
    """Make a request to the NWS API with proper error handling."""
    headers = {"User-Agent": USER_AGENT, "Accept": "application/geo+json"}
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(url, headers=headers, timeout=30.0)
            response.raise_for_status()
            return response.json()
        except Exception:
            return None

@mcp.tool()
async def get_alerts(state: str) -> str:
    """Get weather alerts for a US state.
    Args:
        state: Two-letter US state code (e.g. CA, NY)
    """
    url = f"{NWS_API_BASE}/alerts/active/area/{state}"
    data = await make_nws_request(url)
    if not data or "features" not in data:
        return "Unable to fetch alerts or no alerts found."
    if not data["features"]:
        return "No active alerts for this state."
    alerts = [f"Event: {f['properties'].get('event')}" for f in data["features"]]
    return "\n---\n".join(alerts)

@mcp.tool()
async def get_forecast(latitude: float, longitude: float) -> str:
    """Get weather forecast for a location.
    Args:
        latitude: Latitude of the location
        longitude: Longitude of the location
    """
    points_url = f"{NWS_API_BASE}/points/{latitude},{longitude}"
    points_data = await make_nws_request(points_url)
    if not points_data:
        return "Unable to fetch forecast data for this location."
    forecast_url = points_data["properties"]["forecast"]
    forecast_data = await make_nws_request(forecast_url)
    if not forecast_data:
        return "Unable to fetch detailed forecast."
    periods = forecast_data["properties"]["periods"]
    forecasts = [f"{p['name']}: {p['detailedForecast']}" for p in periods[:5]]
    return "\n---\n".join(forecasts)

def main():
    mcp.run(transport="stdio")

if __name__ == "__main__":
    main()
```

> **IMPORTANT — Logging in STDIO servers:** Never use `print()` with STDIO transport — it corrupts JSON-RPC messages. Use `print(..., file=sys.stderr)` or `logging.info(...)` instead.

---

## 9. TESTING WITH MCP INSPECTOR

The **MCP Inspector** is a browser-based development and testing tool for MCP servers. It is part of the Python MCP SDK.

### Starting the Inspector

```bash
mcp dev mcp_server.py
```

Opens at: `http://127.0.0.1:6274`

### Inspector Interface

The Inspector has these key elements:
- **Transport Type** selector (STDIO)
- **Connect / Disconnect** buttons + connection status indicator
- **Tabs:** Resources | Prompts | Tools | Ping | Sampling | Roots
- **History Panel** — shows all calls made (e.g., `initialize → resources/list → tools/call`)
- **Server Notifications** panel — real-time server updates
- **Response Panel** — JSON response from server

### Testing Workflow

```
1. Run:  mcp dev mcp_server.py
2. Open: http://127.0.0.1:6274
3. Click "Connect" → status changes to "Connected"
4. Click "Tools" tab → "List Tools" → see all tool definitions
5. Select a tool → fill in input fields → click "Run Tool"
6. Verify success status and returned data
7. Test interactions: edit a doc → read it back → confirm change persisted
```

### What Inspector Shows

| Panel | Shows |
|-------|-------|
| Resources tab | Direct resources (e.g., `docs://documents`) + Resource Templates (e.g., `fetch_doc`) |
| Tools tab | All available tools with schemas |
| Prompts tab | All available prompts with parameters |
| History | Sequence: `1.initialize → 2.resources/list → 3.resources/templates/list → 4.resources/read` |
| Response | Full JSON response including `uri`, `mimeType`, `text` content |

### Development Workflow Benefits

- Quickly iterate on tool implementations
- Test edge cases and error conditions
- Verify tool interactions and state management
- Debug issues in real-time without connecting a full application
- Immediate feedback loop → catch issues early

---

## 10. BUILDING AN MCP CLIENT (PYTHON SDK)

### Understanding Client Architecture

In most projects, you build EITHER an MCP client OR an MCP server — not both. Building both here teaches how they work together.

**Two components:**
1. **MCP Client** (your custom class) — wraps the session for easier use
2. **Client Session** (from MCP SDK) — the actual connection to the server

### Project Setup

```bash
uv init mcp-client
cd mcp-client
uv venv
source .venv/bin/activate   # Windows: .venv\Scripts\activate

uv add mcp anthropic python-dotenv

# Create .env file with API key
echo "ANTHROPIC_API_KEY=your-key-here" > .env
echo ".env" >> .gitignore
```

### Complete MCP Client Code

```python
import asyncio
import json
from typing import Optional, Any
from contextlib import AsyncExitStack

from mcp import ClientSession, StdioServerParameters, types
from mcp.client.stdio import stdio_client
from pydantic import AnyUrl

from anthropic import Anthropic
from dotenv import load_dotenv

load_dotenv()

class MCPClient:
    def __init__(self):
        self.session: Optional[ClientSession] = None
        self.exit_stack = AsyncExitStack()
        self.anthropic = Anthropic()

    # --- Server Connection ---
    async def connect_to_server(self, server_script_path: str):
        """Connect to an MCP server (Python or Node.js script)"""
        is_python = server_script_path.endswith('.py')
        is_js = server_script_path.endswith('.js')
        if not (is_python or is_js):
            raise ValueError("Server script must be a .py or .js file")

        command = "python" if is_python else "node"
        server_params = StdioServerParameters(
            command=command,
            args=[server_script_path],
            env=None
        )

        stdio_transport = await self.exit_stack.enter_async_context(
            stdio_client(server_params)
        )
        self.stdio, self.write = stdio_transport
        self.session = await self.exit_stack.enter_async_context(
            ClientSession(self.stdio, self.write)
        )

        await self.session.initialize()

        response = await self.session.list_tools()
        print("\nConnected to server with tools:", [tool.name for tool in response.tools])

    # --- Tools ---
    async def list_tools(self) -> list[types.Tool]:
        result = await self.session.list_tools()
        return result.tools

    async def call_tool(
        self, tool_name: str, tool_input: dict
    ) -> types.CallToolResult | None:
        return await self.session.call_tool(tool_name, tool_input)

    # --- Resources ---
    async def read_resource(self, uri: str) -> Any:
        result = await self.session.read_resource(AnyUrl(uri))
        resource = result.contents[0]
        if isinstance(resource, types.TextResourceContents):
            if resource.mimeType == "application/json":
                return json.loads(resource.text)
        return resource.text

    # --- Prompts ---
    async def list_prompts(self) -> list[types.Prompt]:
        result = await self.session.list_prompts()
        return result.prompts

    async def get_prompt(self, prompt_name: str, args: dict[str, str]):
        result = await self.session.get_prompt(prompt_name, args)
        return result.messages

    # --- Query Processing ---
    async def process_query(self, query: str) -> str:
        """Process a query using Claude and available tools"""
        messages = [{"role": "user", "content": query}]

        response = await self.session.list_tools()
        available_tools = [{
            "name": tool.name,
            "description": tool.description,
            "input_schema": tool.inputSchema
        } for tool in response.tools]

        # Initial Claude API call
        response = self.anthropic.messages.create(
            model="claude-sonnet-4-20250514",
            max_tokens=1000,
            messages=messages,
            tools=available_tools
        )

        final_text = []
        assistant_message_content = []

        for content in response.content:
            if content.type == 'text':
                final_text.append(content.text)
                assistant_message_content.append(content)
            elif content.type == 'tool_use':
                tool_name = content.name
                tool_args = content.input

                result = await self.session.call_tool(tool_name, tool_args)
                final_text.append(f"[Calling tool {tool_name} with args {tool_args}]")

                assistant_message_content.append(content)
                messages.append({"role": "assistant", "content": assistant_message_content})
                messages.append({
                    "role": "user",
                    "content": [{
                        "type": "tool_result",
                        "tool_use_id": content.id,
                        "content": result.content
                    }]
                })

                response = self.anthropic.messages.create(
                    model="claude-sonnet-4-20250514",
                    max_tokens=1000,
                    messages=messages,
                    tools=available_tools
                )
                final_text.append(response.content[0].text)

        return "\n".join(final_text)

    # --- Chat Loop ---
    async def chat_loop(self):
        print("\nMCP Client Started! Type 'quit' to exit.")
        while True:
            try:
                query = input("\nQuery: ").strip()
                if query.lower() == 'quit':
                    break
                response = await self.process_query(query)
                print("\n" + response)
            except Exception as e:
                print(f"\nError: {str(e)}")

    async def cleanup(self):
        await self.exit_stack.aclose()


async def main():
    import sys
    if len(sys.argv) < 2:
        print("Usage: python client.py <path_to_server_script>")
        sys.exit(1)

    client = MCPClient()
    try:
        await client.connect_to_server(sys.argv[1])
        await client.chat_loop()
    finally:
        await client.cleanup()

if __name__ == "__main__":
    asyncio.run(main())
```

### Running the Client

```bash
# Test client against your server
uv run mcp_client.py

# Run full application
uv run main.py

# Usage with specific server
python client.py mcp_server.py
```

---

## 11. RESOURCES IN MCP

### What Resources Enable

Resources allow you to expose data to clients — similar to GET request handlers in an HTTP server. Perfect for **fetching information** rather than performing actions.

### Document Mention Feature Example

When a user types `@document_name`:
1. System fetches list of all docs (for autocomplete) → uses `docs://documents` resource
2. When doc is mentioned → fetches contents → `docs://documents/{doc_id}` resource
3. System injects doc content directly into the prompt sent to Claude
4. Claude doesn't need to use a tool to get the info — it's already in context

### Resource Request Flow

```
User types "@..."    Our Code                MCP Client       MCP Server
       │                  │                      │                 │
       │──"What's in @.." │                      │                 │
       │                  │──ReadResourceRequest──►─docs://docs──►  │
       │                  │◄──ReadResourceResult──◄─[doc names]──── │
       │   (autocomplete  │                      │                 │
       │    populated)    │                      │                 │
```

### Resource Content Type Handling

```python
async def read_resource(self, uri: str) -> Any:
    result = await self.session.read_resource(AnyUrl(uri))
    resource = result.contents[0]

    if isinstance(resource, types.TextResourceContents):
        if resource.mimeType == "application/json":
            return json.loads(resource.text)   # Parse JSON

    return resource.text   # Return raw text
```

### Resources vs. Tools

| Aspect | Resources | Tools |
|--------|-----------|-------|
| Nature | Read-only (GET pattern) | Action-performing |
| Control | Application retrieves and decides how to use | Model decides when to call |
| Example | `docs://documents/report.pdf` | `edit_document(doc_id, old, new)` |
| Use Case | Provide context before AI processes | Perform operations during AI processing |

---

## 12. PROMPTS IN MCP

### Why Prompts Exist

Users can ask Claude to do most tasks directly, but a well-crafted prompt from an expert server author will give **consistently better results** than user-written instructions.

As the MCP server author, you:
- Spend time crafting and testing prompts
- Encode domain knowledge
- Handle edge cases and best practices
- Users benefit without needing to become prompt engineers

### Complete Prompt Workflow

```
1. User types "/" → available prompts appear as commands
2. User selects "format" → prompted to choose a document
3. System sends complete pre-built prompt to Claude
4. Claude receives formatting instructions + document ID
5. Claude uses tools to fetch and process content
6. Result: clean markdown with proper structure
```

### Prompts in MCP Inspector

The inspector shows you **exactly what messages** will be sent to Claude, including how variables get interpolated into the template.

```
Prompts Tab → Select "format" → Enter doc_id → View interpolated prompt
```

---

## 13. TRANSPORT MECHANISMS

### Two Transport Types ★

| Transport | Mechanism | Best For | Performance | Auth Support |
|-----------|-----------|----------|-------------|-------------|
| **STDIO** | Standard input/output streams | Local servers on same machine | Optimal — no network overhead | N/A (local process) |
| **Streamable HTTP** | HTTP POST + optional Server-Sent Events (SSE) | Remote server communication | Network latency applies | Bearer tokens, API keys, custom headers, **OAuth recommended** |

### STDIO Transport

- Uses **stdin/stdout** for direct local process communication
- **No network overhead** → optimal for local servers
- When Claude Desktop launches the filesystem server → server runs locally via STDIO
- STDIO servers typically serve **a single MCP client**

> ⚠️ **CRITICAL:** In STDIO servers, NEVER write to stdout. It corrupts JSON-RPC. Use `sys.stderr` or logging to files.

### Streamable HTTP Transport

- Uses **HTTP POST** for client-to-server messages
- Optional **Server-Sent Events (SSE)** for streaming capabilities
- Enables **remote server communication**
- Remote servers typically serve **many MCP clients** simultaneously
- MCP recommends using **OAuth** to obtain authentication tokens

### Transport Agnosticism

The transport layer **abstracts communication details** from the data layer. The same **JSON-RPC 2.0 message format** works across all transport mechanisms. Your application logic doesn't change when you switch transports.

---

## 14. CONNECTING TO CLAUDE DESKTOP

### Step-by-Step: Local MCP Server → Claude Desktop

1. **Find the config file:**
   - macOS: `~/Library/Application Support/Claude/claude_desktop_config.json`
   - Windows: `%APPDATA%\Claude\claude_desktop_config.json`

2. **Add your server config:**

```json
{
  "mcpServers": {
    "weather": {
      "command": "uv",
      "args": [
        "--directory",
        "C:/ABSOLUTE/PATH/TO/weather",
        "run",
        "weather.py"
      ]
    }
  }
}
```

> **Windows Note:** Use double backslashes `\\` or forward slashes `/` in JSON paths.

3. **Save and restart Claude Desktop**

4. **Verify:** Click the `+` icon in Claude → "Connectors" → your server should appear

5. **Test:**
   - "What's the weather in Sacramento?"
   - "What are the active weather alerts in Texas?"

### Under-the-Hood Flow

```
1. User asks Claude a question
2. Claude analyzes available tools → decides which to use
3. Client executes chosen tool(s) through the MCP server
4. Results sent back to Claude
5. Claude formulates natural language response
6. Response displayed to user
```

---

## 15. REMOTE MCP SERVERS

### What Are Remote MCP Servers?

Remote MCP servers function like local MCP servers but are **hosted on the internet** rather than your local machine.

**Key Advantage:** Available from any MCP client with internet connection — no installation/configuration on each device.

**Ideal For:**
- Web-based AI applications
- Integrations prioritizing ease of use
- Services requiring server-side processing or authentication

### Connecting Claude to a Remote MCP Server (via Custom Connectors)

1. **Navigate to Settings → Connectors** (via profile icon → Settings → Connectors)
2. **Click "Add custom connector"** (scroll to bottom of Connectors section)
3. **Enter the remote MCP server URL** (must include `https://` protocol)
4. **Click "Add" → Complete authentication** (OAuth, API key, or username/password)
5. **Access resources via paperclip icon** in message input
6. **Configure tool permissions** in Connectors settings (enable/disable specific tools)

### Best Practices for Remote MCP Servers

- **Verify authenticity** before connecting — only trusted sources
- **Review permissions** requested during authentication
- **Regularly remove** connectors no longer in use
- **Manage multiple connectors** by organizing by purpose or project

---

## 16. MCP VERSIONING

### Version Format

MCP uses **string-based version identifiers** in the format `YYYY-MM-DD` — the last date backwards-incompatible changes were made.

**Current version:** `2025-11-25`

### Revision Types

| Type | Status |
|------|--------|
| **Draft** | In-progress, not ready for use |
| **Current** | Ready for use; may receive backwards-compatible changes |
| **Final** | Past, complete specifications — will not change |

### Version Negotiation

- Happens during initialization in the `protocolVersion` field
- Clients and servers **MAY** support multiple versions simultaneously
- They **MUST** agree on a single version to use for the session
- If negotiation fails → client gracefully terminates connection

---

## 17. UV — PYTHON PACKAGE MANAGER

**uv** is the fast, modern Python package manager used to run MCP projects.

### Installation

```bash
# Windows (PowerShell)
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"

# macOS/Linux
curl -LsSf https://astral.sh/uv/install.sh | sh

# Via pip
pip install uv

# Via WinGet (Windows)
winget install --id=astral-sh.uv -e

# Via Homebrew (macOS)
brew install uv
```

### Key Commands

| Command | Purpose |
|---------|---------|
| `uv init project-name` | Create new Python project |
| `uv venv` | Create virtual environment |
| `uv add mcp anthropic` | Install packages |
| `uv run mcp_server.py` | Run a Python script |
| `uv run main.py` | Run the main application |
| `uv self update` | Update uv itself |
| `uv cache clean` | Clean uv cache |
| `mcp dev mcp_server.py` | Start MCP Inspector |

### Uninstalling uv (Windows)

```bash
uv cache clean
rm $HOME\.local\bin\uv.exe
rm $HOME\.local\bin\uvx.exe
```

---

## 18. KEY DEFINITIONS GLOSSARY

| Term | Definition |
|------|-----------|
| **MCP (Model Context Protocol)** | Open-source standard ("USB-C for AI") for connecting AI applications to external systems (data sources, tools, workflows) |
| **MCP Host** | The AI application (e.g., Claude Desktop, VS Code) that manages one or multiple MCP clients |
| **MCP Client** | Protocol-level component that maintains a dedicated connection to exactly one MCP server |
| **MCP Server** | A program that exposes tools, resources, and/or prompts to MCP clients; can run locally or remotely |
| **Tool (MCP Primitive)** | Executable function that the AI model decides when and how to invoke; performs actions on external systems |
| **Resource (MCP Primitive)** | Read-only data source that the application retrieves and provides as context to the AI model |
| **Prompt (MCP Primitive)** | Reusable parameterized instruction template that users explicitly invoke |
| **STDIO Transport** | Communication via standard input/output streams; used for local MCP servers; no network overhead |
| **Streamable HTTP** | Communication via HTTP POST + optional SSE; used for remote MCP servers; supports OAuth |
| **JSON-RPC 2.0** | The underlying RPC protocol MCP uses for all client-server communication |
| **Lifecycle Management** | Handling connection initialization, capability negotiation, and connection termination |
| **Capability Negotiation** | Process during initialization where client and server declare supported features |
| **Elicitation** | Client primitive allowing MCP servers to request specific information from users during interactions |
| **Sampling** | Client primitive allowing MCP servers to request LLM completions through the client |
| **Roots** | Client primitive letting clients specify which directories servers should focus on |
| **FastMCP** | Python SDK class for building MCP servers using decorators and type hints |
| **MCP Inspector** | Browser-based dev tool for testing MCP servers interactively (`mcp dev server.py`) |
| **Direct Resource** | Resource with static URI — no parameters, fixed data |
| **Templated Resource** | Resource with parameterized URI — dynamic data based on arguments |
| **Notifications** | JSON-RPC messages (no `id`) sent from server to client for real-time updates; no response expected |
| **uv** | Fast, modern Python package manager used to set up and run MCP projects |
| **Claude Desktop Config** | JSON file (`claude_desktop_config.json`) where you register MCP servers for Claude Desktop |
| **Custom Connectors** | Bridge in Claude.ai connecting it to remote MCP servers |

---

## 19. INTERVIEW QUESTIONS & ANSWERS

**Q1: What is MCP and why does it matter?**

**A:** MCP (Model Context Protocol) is an open-source standard for connecting AI applications to external systems. Think of it as "USB-C for AI" — just like USB-C provides a universal way to connect electronic devices, MCP provides a universal way for AI applications to connect to any external tool, data source, or workflow.

It matters because without MCP, every developer who wants Claude to interact with a service like GitHub must write ALL the integration code themselves — every tool schema, every function, every API call handler. MCP shifts this burden to dedicated MCP servers, dramatically reducing development time and maintenance burden. Because it's an open standard, any developer can build a server for any tool, and those servers work with any MCP-compatible client (Claude, ChatGPT, VS Code, Cursor, etc.).

---

**Q2: Explain the MCP Architecture — what are the Host, Client, and Server?**

**A:**
- **MCP Host** is the AI application that users interact with — for example Claude Desktop or VS Code. The Host manages and coordinates one or multiple MCP Clients.

- **MCP Client** is a protocol-level component (typically an object in your code) that maintains a dedicated connection to exactly ONE MCP Server. The Host creates one MCP Client per MCP Server it connects to.

- **MCP Server** is a program that exposes tools, resources, and/or prompts to MCP Clients. It can run locally (STDIO transport) or remotely (Streamable HTTP transport).

The relationship: One Host → Many Clients → Each Client ↔ One Server → External Service.

---

**Q3: What are the three MCP primitives and how do they differ?**

**A:**
- **Tools** are executable functions that the **AI model** decides when and how to invoke. They perform actions — API calls, file operations, database queries. Example: `send_email()`, `search_flights()`.

- **Resources** are read-only data sources that the **application** retrieves and presents as context. The app controls what to fetch and when. Example: `docs://documents/report.pdf` returns file contents.

- **Prompts** are pre-built instruction templates that **users** explicitly invoke (e.g., typing `/format`). They encode domain expertise and give better results than user-written instructions.

Memory aid: Tools = AI **does** things. Resources = App **reads** data. Prompts = User **selects** templates.

---

**Q4: What is the difference between STDIO and Streamable HTTP transport?**

**A:**
- **STDIO transport** uses standard input/output streams for direct local process communication. It's used when the MCP server runs on the same machine as the client. It has no network overhead — optimal performance — and serves typically a single MCP client.

- **Streamable HTTP transport** uses HTTP POST for client-to-server messages with optional Server-Sent Events for streaming. It's used for remote MCP servers hosted on the internet, and supports OAuth, API keys, and bearer tokens for authentication. Remote servers can serve many MCP clients simultaneously.

The key insight: both use JSON-RPC 2.0 for message format — the transport layer is abstracted away from the data/protocol layer.

---

**Q5: What is Elicitation in MCP and why is it useful?**

**A:** Elicitation is a client primitive that enables MCP servers to dynamically request specific information from users during an interaction — rather than requiring all information upfront or failing when data is missing.

For example: a travel booking server has processed flight options and now needs to confirm seat preference (window/aisle) and room type before finalizing. The server sends an `elicitation/create` request with a schema defining the needed fields. The client presents appropriate UI, the user fills in the info, and the server receives the response and continues processing.

This creates more flexible, adaptive workflows — servers can pause and adapt to user needs rather than following rigid upfront data requirements.

---

**Q6: What is the MCP Inspector and when do you use it?**

**A:** The MCP Inspector is a browser-based development and testing tool included in the MCP Python SDK. You start it with `mcp dev mcp_server.py` and it opens at `http://127.0.0.1:6274`.

You use it during MCP server development to:
- Test all your tools, resources, and prompts interactively without connecting to a full application
- Verify that tools execute correctly and return expected data
- Test resource reading and template parameter handling
- Confirm prompt interpolation is correct
- Debug issues in real-time with an immediate feedback loop

It shows you the complete call history (initialize → resources/list → tools/call) and full JSON responses, making it easy to spot issues early.

---

**Q7: Explain Sampling in MCP and why a server would use it.**

**A:** Sampling is a client primitive that allows MCP servers to request language model completions from the client's AI application — without integrating an LLM themselves.

A server would use this when it needs AI reasoning but wants to stay **model-independent**. For example, a travel booking server needs to analyze 47 flight options and recommend the best one. Rather than embedding a model SDK, the server sends a `sampling/createMessage` request to the client, which forwards it to Claude. Claude analyzes the flights and returns a recommendation.

The key benefit is that the client maintains **complete control** over user permissions and security. The design includes human-in-the-loop checkpoints — users can review and approve both the initial sampling request AND the AI's response before it returns to the server.

---

**Q8: Walk me through what happens when a user asks Claude "What repositories do I have?"**

**A:** Using the GitHub MCP server example:

1. User submits query to the application
2. Application needs to know available tools → asks MCP Client for `tools/list`
3. MCP Client sends `ListToolsRequest` to GitHub MCP Server
4. Server responds with `ListToolsResult` — tool definitions including `get_repos()`
5. Application sends user query + available tools to Claude
6. Claude analyzes and decides it needs `get_repos` tool
7. Claude returns `ToolUse` response indicating the tool call
8. Application asks MCP Client to execute that tool: `CallToolRequest`
9. MCP Client forwards to GitHub MCP Server → Server calls GitHub API
10. GitHub API returns repository data → Server returns `CallToolResult`
11. Application sends tool result back to Claude
12. Claude formulates natural language response
13. User receives: "Your repositories are..."

---

## 20. CERTIFICATION QUESTIONS WITH ANSWERS

**Q1.** MCP is often described as "USB-C for AI." What does this analogy mean?

- A) MCP is a hardware protocol
- B) MCP provides a universal standardized way to connect AI applications to external tools
- C) MCP is only for USB devices
- D) MCP is a proprietary Anthropic protocol

**Answer: B** — MCP provides a universal, consistent interface for AI applications to connect to any external system — just as USB-C provides a universal way to connect electronic devices regardless of manufacturer.

---

**Q2.** Which MCP primitive is controlled by the AI MODEL — meaning the model decides when and how to invoke it?

- A) Resources
- B) Prompts
- C) Tools
- D) Elicitation

**Answer: C — Tools** are model-controlled. Resources are application-controlled. Prompts are user-controlled.

---

**Q3.** What is the correct sequence of the MCP lifecycle during initialization?

- A) tools/list → initialize → notifications/initialized
- B) initialize → notifications/initialized → tools/list
- C) notifications/initialized → initialize → tools/list
- D) tools/list → notifications/initialized → initialize

**Answer: B** — Initialize (capability negotiation) → notifications/initialized (ready signal) → tools/list (tool discovery). This is the correct MCP startup sequence.

---

**Q4.** What transport should you use for an MCP server running locally on the same machine as the client?

- A) Streamable HTTP
- B) WebSocket
- C) STDIO
- D) gRPC

**Answer: C — STDIO** — uses stdin/stdout for direct process communication; optimal performance with no network overhead. Best for local servers.

---

**Q5.** In the MCP Python SDK (FastMCP), which decorator is used to register a resource with a static URI?

- A) `@mcp.tool()`
- B) `@mcp.prompt()`
- C) `@mcp.resource("static://uri")`
- D) `@mcp.endpoint()`

**Answer: C** — `@mcp.resource("docs://documents")` with a static URI registers a direct resource. Templated resources use parameter syntax: `@mcp.resource("docs://documents/{doc_id}")`.

---

**Q6.** What happens when an MCP server sends a notification message (no `id` field)?

- A) The client must send an error response
- B) The client must respond with an acknowledgment
- C) No response is expected or sent
- D) The connection is terminated

**Answer: C** — Notifications follow JSON-RPC 2.0 notification semantics: no `id` means no response is expected. The client simply processes the notification (e.g., refreshing the tool list on `notifications/tools/list_changed`).

---

**Q7.** Which client primitive allows an MCP server to request language model completions without directly integrating an LLM?

- A) Elicitation
- B) Roots
- C) Logging
- D) Sampling

**Answer: D — Sampling** — allows servers to request LLM completions through the client's AI application (e.g., Claude), keeping the server model-independent.

---

**Q8.** Which command starts the MCP Inspector for browser-based server testing?

- A) `uv run mcp_server.py`
- B) `mcp start mcp_server.py`
- C) `mcp dev mcp_server.py`
- D) `uv test mcp_server.py`

**Answer: C** — `mcp dev mcp_server.py` starts the MCP Inspector, accessible at `http://127.0.0.1:6274`.

---

**Q9.** What is the difference between a Direct Resource and a Templated Resource in MCP?

- A) Direct resources support write operations; templated are read-only
- B) Direct resources have static URIs; templated resources have parameterized URIs
- C) Templated resources require OAuth; direct resources do not
- D) Direct resources are faster because they bypass the transport layer

**Answer: B** — Direct resources: fixed URI (`docs://documents`) — always returns the same dataset. Templated resources: parameterized URI (`docs://documents/{doc_id}`) — SDK parses parameters and passes them as function arguments for dynamic data.

---

**Q10.** CRITICAL: Why should you never use `print()` in a STDIO-based MCP server?

- A) It causes memory leaks
- B) It writes to stdout, which corrupts the JSON-RPC message stream
- C) The MCP SDK does not support Python's print function
- D) It blocks the event loop

**Answer: B** — STDIO MCP servers use stdout exclusively for JSON-RPC messages. Any `print()` call writes to stdout and corrupts the message stream, breaking communication with the MCP client. Use `print(..., file=sys.stderr)` or `logging.info()` instead.

---

## 21. ONE-PAGE QUICK REVISION SUMMARY

```
╔══════════════════════════════════════════════════════════════════════╗
║             MODULE 3 — MCP QUICK REFERENCE                           ║
╠══════════════════════════════════════════════════════════════════════╣
║ WHAT IS MCP                                                          ║
║ Open standard ("USB-C for AI") connecting AI to external systems     ║
║ Solves: stops you writing ALL integration code from scratch          ║
║                                                                      ║
║ ARCHITECTURE  ★                                                      ║
║ Host (AI App) → Client(s) [1 per server] → Server(s) → Ext. Services║
║                                                                      ║
║ LAYERS                                                               ║
║ Data Layer: JSON-RPC 2.0 protocol, lifecycle, primitives             ║
║ Transport Layer: STDIO (local, no network) | Streamable HTTP (remote)║
║                                                                      ║
║ THREE PRIMITIVES  ★★★                                                ║
║ Tools     → Model-controlled ACTIONS  | tools/list, tools/call       ║
║ Resources → App-controlled READ data  | resources/list, resources/read║
║ Prompts   → User-selected TEMPLATES   | prompts/list, prompts/get    ║
║                                                                      ║
║ CLIENT PRIMITIVES (Server → Client)                                  ║
║ Sampling: Server requests LLM completion from client (model-agnostic)║
║ Elicitation: Server dynamically requests info from users             ║
║ Roots: Client scopes filesystem access for servers                   ║
║ Logging: Server sends debug/info messages to client                  ║
║                                                                      ║
║ LIFECYCLE  ★                                                         ║
║ initialize → notifications/initialized → tools/list → tools/call    ║
║                                                                      ║
║ PYTHON SDK                                                            ║
║ FastMCP + @mcp.tool / @mcp.resource / @mcp.prompt decorators         ║
║ Type hints → auto JSON schema generation                             ║
║ NEVER print() to stdout in STDIO servers → use stderr                ║
║                                                                      ║
║ TESTING                                                              ║
║ mcp dev mcp_server.py → browser at http://127.0.0.1:6274            ║
║ Inspector shows: Resources | Prompts | Tools | History | Responses   ║
║                                                                      ║
║ RESOURCES                                                            ║
║ Direct:    static URI → e.g. docs://documents                        ║
║ Templated: param URI  → e.g. docs://documents/{doc_id}               ║
║ MIME types: application/json | text/plain | application/pdf          ║
║                                                                      ║
║ TRANSPORT                                                            ║
║ STDIO: local only, single client, no network overhead                ║
║ HTTP+SSE: remote, multi-client, OAuth/API key auth                   ║
║                                                                      ║
║ VERSIONING                                                           ║
║ Format: YYYY-MM-DD | Current: 2025-11-25                             ║
║ Types: Draft | Current | Final                                       ║
║                                                                      ║
║ UV COMMANDS                                                          ║
║ uv init / uv venv / uv add mcp / uv run main.py / mcp dev server.py ║
╚══════════════════════════════════════════════════════════════════════╝
```

---

## 22. TOP 20 MCP KEY TAKEAWAYS

1. **MCP = "USB-C for AI"** — open standard for connecting AI applications to external systems.

2. **The core problem MCP solves:** Developers no longer need to write all tool schemas and integration functions themselves.

3. **Three participants:** MCP Host (AI app) → MCP Client (one per server) → MCP Server (exposes capabilities).

4. **One-to-many:** One MCP Host manages MANY MCP Clients; each client connects to exactly ONE server.

5. **Two layers:** Data Layer (JSON-RPC 2.0 protocol) and Transport Layer (STDIO or Streamable HTTP).

6. **Three core primitives:** Tools (model decides), Resources (app retrieves), Prompts (user selects).

7. **Tools are model-controlled.** The AI decides when and how to call them based on conversation context.

8. **Resources are application-controlled.** The app fetches data and decides how to present it to the model.

9. **Prompts are user-controlled.** They require explicit invocation (e.g., `/format doc_id`).

10. **STDIO = local, no network, single client.** Streamable HTTP = remote, OAuth-supported, multi-client.

11. **MCP Lifecycle:** Initialize → Negotiate capabilities → List tools → Execute tools → Receive notifications.

12. **Notifications use no `id` field** — one-way messages; no response expected. Used for real-time updates.

13. **FastMCP Python SDK** uses decorators (`@mcp.tool`, `@mcp.resource`, `@mcp.prompt`) — auto-generates JSON schemas from type hints.

14. **CRITICAL:** Never `print()` to stdout in STDIO servers — it corrupts JSON-RPC. Use `sys.stderr`.

15. **MCP Inspector** (`mcp dev mcp_server.py`) — browser-based at port 6274. Essential for development testing.

16. **Direct Resources** have static URIs. **Templated Resources** have parameterized URIs (`{param}`).

17. **Sampling** enables servers to request LLM completions from the client without embedding their own model.

18. **Elicitation** enables servers to dynamically gather user input during execution — creates adaptive workflows.

19. **MCP is transport-agnostic:** same JSON-RPC 2.0 protocol works over STDIO, HTTP, WebSockets.

20. **MCP versioning format:** `YYYY-MM-DD`. Version negotiation during initialization; terminate if incompatible.

---

> [!NOTE]
> **Exam Quick Reminders:**
> - MCP ≠ tool use — they're complementary. MCP = who defines tools. Tool use = how Claude calls them.
> - `tools/list` → `tools/call` — always list first, then call.
> - `notifications/tools/list_changed` → no `id` field → no response needed → client re-runs `tools/list`.
> - STDIO server: **never** write to stdout. Always `sys.stderr`.
> - `mcp dev mcp_server.py` → port 6274 → MCP Inspector.
> - FastMCP: Python type hints → JSON Schema auto-generation.
> - Sampling = model-independent server requesting AI help. Elicitation = server requesting user input.

---

*📅 Last Updated: May 2026*
*📁 Source: `E:\Teja_Interview_preparation\Claude_Preparation\MCP-Model Context Protocol\`*
*🎯 Module 3 ONLY — For Module 1 see `Claude_Module1_AI_Fluency.md` | For Module 2 see `Claude_Module2_Preparation_Notes.md`*
