# Claude with Google Cloud's Vertex AI — Certification Preparation Notes

> **Role:** Expert AI Trainer | Certification Mentor | Technical Content Architect
> **Scope:** Claude API on Vertex AI, Prompt Engineering, Tool Use, RAG, MCP, Agents & Workflows
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
14. [Most Important Topics to Revise](#14-most-important-topics-to-revise)
15. [Certification Preparation Roadmap](#15-certification-preparation-roadmap)

---

## 1. Course / Topic Overview

### 📌 Beginner Explanation
This course teaches you how to integrate **Anthropic's Claude AI models** into applications using **Google Cloud's Vertex AI** platform. You learn how to:
- Make API calls to Claude securely via a server
- Handle conversations, streaming, and structured output
- Build real-world tools, agents, and RAG pipelines

### 📌 Intermediate Understanding
The course covers the **complete developer lifecycle**:
- Request/response pipeline through Vertex AI
- Advanced prompt engineering (temperature, stop sequences, prefilling)
- Tool use (custom + built-in tools), multi-turn conversations
- RAG (Retrieval Augmented Generation) with embeddings, vector DBs, and hybrid search
- **Model Context Protocol (MCP)** — the new standard for integrating AI with external services

### 📌 Advanced Insights
At an advanced level, this covers:
- Agentic architectures (workflows vs. agents)
- Prompt caching for cost/performance optimization
- Extended thinking (reasoning) with signature security
- Contextual retrieval, re-ranking, and BM25 hybrid search
- Building full MCP Clients/Servers with resources, tools, and prompts
- Workflow patterns: Chaining, Parallelization, Routing, Evaluator-Optimizer

---

## 2. Core Concepts

### 🔑 2.1 The Complete Request Lifecycle (5 Steps)

| Step | Description |
|------|-------------|
| **1. Request to Server** | Client sends user input to your backend server |
| **2. Request to Vertex** | Your server calls Claude via Anthropic SDK through Vertex AI |
| **3. Model Processing** | Claude tokenizes, embeds, contextualizes, and generates response |
| **4. Response to Server** | Vertex sends back the generated text + metadata |
| **5. Response to Client** | Your server forwards the result to the client application |

> **CERT ALERT:** Never make direct API calls from client-side code. Always use a server intermediary to protect API credentials.

---

### 🔑 2.2 Inside Claude — Text Generation (4 Stages)

| Stage | What Happens |
|-------|-------------|
| **Tokenization** | Input text broken into tokens (words, sub-words, symbols) |
| **Embedding** | Each token → numerical vector (embedding) capturing meaning |
| **Contextualization** | Embeddings adjusted based on surrounding words (attention mechanism) |
| **Generation** | Probabilities computed for next token; sampling with temperature |

**When does generation stop?**
- `max_tokens` reached
- Natural end-of-sequence token generated
- `stop_sequence` string encountered

---

### 🔑 2.3 Conversation State Management
- **Claude is stateless** — no memory between API calls
- You must **maintain and send the full conversation history** with every request
- Messages use `role: "user"` or `role: "assistant"` with `content` text

---

### 🔑 2.4 System Prompts
- Control Claude's **tone, role, and behavior**
- Defined as plain strings; passed as `system` parameter
- Claude tries to respond as if it were the described persona
- **Best practice:** Never hard-code system prompts — make them configurable

---

### 🔑 2.5 Temperature

| Temperature Range | Use Case |
|------------------|----------|
| **0.0 – 0.3** | Factual Q&A, code generation, data extraction, content moderation |
| **0.4 – 0.7** | Summarization, educational content, problem solving |
| **0.8 – 1.0** | Brainstorming, creative writing, marketing, joke generation |

---

### 🔑 2.6 Response Streaming
- Instead of waiting for complete response, receive text **chunk by chunk**
- **Stream Events:** `MessageStart` → `ContentBlockStart` → `ContentBlockDelta` (actual text) → `ContentBlockStop` → `MessageDelta` → `MessageStop`
- In production: forward chunks via **WebSockets** or **Server-Sent Events (SSE)**

---

### 🔑 2.7 Prefilled Assistant Messages
- Add an `assistant` message at the end of the messages list
- Claude **continues from where you left off**
- **Use case:** Force specific output format, steer response direction
- Combined with stop sequences = precise output extraction

---

### 🔑 2.8 Stop Sequences
- Claude stops generating when a specified string is encountered
- The stop sequence itself is **NOT included** in the output
- **Use case:** Limit list length, extract JSON cleanly, control output boundaries

---

## 3. Important Definitions

| Term | Definition |
|------|-----------|
| **Token** | Smallest unit of text Claude processes (word, sub-word, or symbol) |
| **Embedding** | Numerical vector representing the semantic meaning of a token |
| **Cosine Similarity** | Measure of similarity between two vectors (1.0=identical, 0.0=unrelated, -1.0=opposite) |
| **Cosine Distance** | 1 - cosine similarity (smaller = more similar) |
| **RAG** | Retrieval Augmented Generation — include relevant document chunks in prompts |
| **Vector Database** | Specialized DB for storing and searching numerical embeddings |
| **BM25** | Best Match 25 — lexical search algorithm weighting term rarity for exact-match retrieval |
| **Chunking** | Breaking documents into smaller pieces for RAG processing |
| **Context Window** | Maximum number of tokens Claude can process in a single request |
| **Prompt Caching** | Storing preprocessing results to reuse in subsequent identical requests |
| **Extended Thinking** | Feature enabling Claude to reason through problems before answering |
| **MCP** | Model Context Protocol — standardized communication layer between AI clients and tool servers |
| **MCP Client** | Your server that connects to MCP Servers to access pre-built tools |
| **MCP Server** | Provides pre-built tool schemas and function implementations for external services |
| **Tool Use** | Mechanism allowing Claude to call external functions to fetch real-time data |
| **Prompt Evaluation** | Automated testing of prompts against a dataset to get objective quality scores |
| **RRF** | Reciprocal Rank Fusion — algorithm for merging results from multiple search systems |
| **Contextual Retrieval** | Adding document-level context to each chunk before indexing to improve search accuracy |
| **Workflow** | Predefined sequence of Claude API calls to solve a known problem |
| **Agent** | Claude + set of tools, autonomously deciding which tools to use to complete a goal |

---

## 4. Tools & Technologies

### 🛠️ 4.1 Anthropic Python SDK with Vertex AI Support

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | `anthropic[vertex]` (Python SDK) |
| **Purpose** | Connect to Claude models hosted on Google Cloud Vertex AI |
| **Key Class** | `AnthropicVertex` |
| **Key Method** | `client.messages.create()` |
| **Installation** | `pip install "anthropic[vertex]"` |
| **Example** | `client = AnthropicVertex(region="global", project_id="your-project-id")` |

---

### 🛠️ 4.2 Google Cloud Vertex AI

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | Vertex AI (Google Cloud Platform) |
| **Purpose** | Enterprise-grade ML platform hosting Anthropic Claude models |
| **Key Feature** | **Model Garden** — marketplace for AI models including Claude |
| **Models Available** | Claude Opus 4, Claude Sonnet 4, Claude 3.5 Haiku, Claude 3 Haiku, Claude 3.5 Sonnet |
| **Setup Path** | Console → Vertex AI → Model Garden → Search "Anthropic" → Enable |
| **Authentication** | `gcloud auth application-default login` |

> **IMAGE INSIGHT (img2):** Google Cloud Console showing Vertex AI left nav — click **Model Garden** to access Claude models.

> **IMAGE INSIGHT (img3):** Model Garden search for "Anthropic" shows: Claude Opus 4, Claude Sonnet 4, Claude 3.5 Haiku, Claude 3 Haiku, Claude 3.5 Sonnet.

> **IMAGE INSIGHT (img4):** Claude Sonnet 4 model page with **Enable** button — must click to activate the model before using it.

---

### 🛠️ 4.3 Google GenAI SDK (for Embeddings)

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | `google-genai` Python SDK |
| **Purpose** | Generate text embeddings using Vertex AI's embedding models |
| **Embedding Model** | `text-embedding-005` |
| **Installation** | `pip install google-genai` |
| **Example** | `client.models.embed_content(model="text-embedding-005", contents=text)` |

---

### 🛠️ 4.4 MCP Python SDK (FastMCP)

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | `mcp` Python SDK (`FastMCP`) |
| **Purpose** | Build MCP servers and clients with minimal boilerplate |
| **Key Class** | `FastMCP` |
| **Key Decorators** | `@mcp.tool()`, `@mcp.resource()`, `@mcp.prompt()` |
| **Testing Tool** | `mcp dev mcp_server.py` → browser inspector at port 6277 |

---

### 🛠️ 4.5 gcloud CLI

| Attribute | Detail |
|-----------|--------|
| **Tool Name** | Google Cloud SDK CLI |
| **Purpose** | Authenticate and configure GCP access for Anthropic SDK |
| **Key Commands** | `gcloud init`, `gcloud auth login`, `gcloud auth application-default login` |

---

### 🛠️ 4.6 Built-In Claude Tools

| Tool | Schema Type | Purpose |
|------|------------|---------|
| **Text Editor Tool** | `text_editor_20250124` (claude-3-7) / `text_editor_20241022` (claude-3-5) | View, edit, create, insert, undo files |
| **Web Search Tool** | `web_search_20250305` | Real-time internet search with citations |

---

## 5. Architecture / Workflows

### 🏗️ 5.1 API Request Architecture

```
[Client App]
     |  (User Input)
     v
[Your Server] ---- [Anthropic SDK] ---- [Vertex AI (Claude)]
     |                                         |
     |<-------- Response (text + metadata) ----+
     v
[Client App] (display response)
```

> **IMAGE INSIGHT (img1):** Shows the 5-step pipeline. Your Server uses Anthropic SDK to send:
> **API Key + Model + Messages + Max Tokens** → Vertex AI

---

### 🏗️ 5.2 Tool Use Flow

```
User Question
     |
     v
[Initial Request to Claude + Tool Schemas]
     |
     v
[Claude Response: stop_reason = "tool_use"]
     |
     v
[Your Code: Execute Tool Function]
     |
     v
[Send tool_result back to Claude]
     |
     v
[Claude: Final Text Answer]
```

**Multi-turn tool conversation loop:**
```python
def run_conversation(messages):
    while True:
        response = chat(messages, tools=[...])
        add_assistant_message(messages, response)
        if response.stop_reason != "tool_use":
            break
        tool_results = run_tools(response)
        add_user_message(messages, tool_results)
    return messages
```

---

### 🏗️ 5.3 RAG Pipeline (5 Steps)

```
PREPROCESSING (done once):
1. Chunk Source Document
         |
2. Generate Embeddings per Chunk (text-embedding-005)
         |
3. Store in Vector Database (with original text)
         |
-------- WAIT FOR USER QUERY --------
         |
QUERY TIME:
4. Embed User Query
         |
5. Search Vector DB --> Cosine Similarity --> Top-K Chunks
         |
6. Build Final Prompt = User Question + Relevant Chunks --> Claude
```

**Hybrid Search (Better Results):**
```
Semantic Search (Vector DB) --+
                               +--> Reciprocal Rank Fusion (RRF) --> Merged Results --> Claude
Lexical Search (BM25)      --+
```

**Optional Enhancement: Re-ranking**
```
Hybrid Results --> LLM Re-ranker (Claude) --> Intelligently Reordered Results --> Claude
```

---

### 🏗️ 5.4 MCP Architecture

```
[Your App / MCP Client]
        |
        | ListToolsRequest / CallToolRequest
        v
[MCP Server (e.g., GitHub MCP)]
        |
        | API Calls
        v
[External Service (GitHub, AWS, DB, etc.)]
```

**MCP Primitives:**

| Primitive | Controlled By | Use Case |
|-----------|--------------|----------|
| **Tools** | Claude (AI Model) | Give Claude capabilities (read files, call APIs) |
| **Resources** | Your Application Code | Fetch data for UI or context injection |
| **Prompts** | User (via UI actions) | Pre-built, high-quality prompt templates |

---

### 🏗️ 5.5 Agentic Workflow Patterns

| Pattern | Description | When to Use |
|---------|-------------|-------------|
| **Chaining** | Output of Step N → Input of Step N+1 | Complex sequential subtasks |
| **Parallelization** | Run multiple specialized tasks simultaneously → Aggregate | Independent subtasks |
| **Routing** | Categorize input → Route to specialized pipeline | Different request types |
| **Evaluator-Optimizer** | Producer → Grader → Feedback loop | Quality-critical output |

---

### 🏗️ 5.6 Prompt Evaluation Workflow (5 Steps)

```
1. Draft Prompt
      |
2. Create Evaluation Dataset (question/task pairs)
      |
3. Run Each Test Case Through Claude
      |
4. Grade Results (Code Grader + Model Grader)
      |
5. Calculate Average Score --> Iterate Prompt --> Repeat
```

**Grader Types:**

| Type | Best For |
|------|---------|
| **Code Grader** | Syntax validation (JSON, Python, Regex) — fast, objective |
| **Model Grader** | Quality, helpfulness, instruction following — flexible |
| **Human Grader** | Most flexible but slowest |

---

### 🏗️ 5.7 Contextual Retrieval Pipeline

```
For Each Chunk:
  Take (chunk + start of document + previous chunks)
         |
         v
  Ask Claude to write a "situating snippet"
         |
         v
  Combine snippet + original chunk = Contextualized Chunk
         |
         v
  Add to Vector + BM25 indexes
```

---

## 6. Key Certification Topics

### CERT TOPIC 1: Vertex AI Setup Steps (4 Steps)
1. Google Cloud Console → Vertex AI → **Model Garden**
2. Search "Anthropic" → Select model → Click **Enable**
3. `gcloud init` + `gcloud auth login`
4. `gcloud config set project YOUR_PROJECT_ID`
5. `gcloud auth application-default login`
6. `pip install "anthropic[vertex]"`

### CERT TOPIC 2: Required API Request Fields
- `model` — Claude model identifier (e.g., `claude-sonnet-4@20250514`)
- `max_tokens` — Response length budget (NOT a target, a ceiling)
- `messages` — List of `{role, content}` dicts

### CERT TOPIC 3: Tool Use Response Structure
- `stop_reason == "tool_use"` signals Claude wants to use a tool
- Response `content` is a **list** of blocks: `TextBlock` + `ToolUseBlock`
- Must send back `tool_result` block with matching `tool_use_id`

### CERT TOPIC 4: Prompt Caching Rules
- Cache lives **5 minutes**
- Requires **minimum 1024 tokens**
- Content must be **100% identical** to hit cache
- Add `cache_control: {type: "ephemeral"}` to last tool, system prompt, or message
- **Caching order: Tools → System Prompt → Messages**

### CERT TOPIC 5: Extended Thinking Requirements
- `thinking_budget` minimum: **1024 tokens**
- `max_tokens` **must exceed** `thinking_budget`
- Response = `thinking block` (with cryptographic signature) + `text block`
- **Redacted thinking** = safety-flagged encrypted block (still usable in conversation history)

### CERT TOPIC 6: Vision/Image Handling Limits
- Max **100 images** per request
- Max **5MB** per image
- Single image: max **8000px** height/width
- Multiple images: max **2000px** each
- Token cost formula: `(width × height) / 750`
- Input formats: `base64` encoding OR URL

### CERT TOPIC 7: RAG Chunking Strategies

| Strategy | Method | Best For |
|----------|--------|---------|
| **Size-based** | Fixed character count + overlap | Unknown/mixed document types |
| **Structure-based** | Split on headers/paragraphs | Markdown, structured docs |
| **Semantic-based** | NLP sentence grouping | High quality, complex docs |

### CERT TOPIC 8: tool_choice Parameter Values
- `{"type": "auto"}` — Claude decides (default)
- `{"type": "any"}` — Claude must use a tool (its choice which)
- `{"type": "tool", "name": "TOOL_NAME"}` — Claude must use specified tool

### CERT TOPIC 9: MCP vs Tool Use
- **Tool Use** = HOW Claude calls external functions
- **MCP** = WHO creates and maintains those tool implementations
- MCP shifts implementation burden from your code to specialized MCP Servers

### CERT TOPIC 10: Workflows vs Agents

| Dimension | Workflow | Agent |
|-----------|----------|-------|
| Path | Predefined | Dynamic |
| Flexibility | Low | High |
| Reliability | High | Lower |
| Cost | Predictable | Variable |
| When to use | Known steps | Unknown steps |

---

## 7. Important Commands / Code Examples

### 7.1 Vertex AI Client Setup
```python
from anthropic import AnthropicVertex

client = AnthropicVertex(region="global", project_id="your-project-id")
model = "claude-sonnet-4@20250514"
```

### 7.2 Basic API Call
```python
message = client.messages.create(
    model=model,
    max_tokens=1000,
    messages=[{"role": "user", "content": "What is quantum computing?"}]
)
# Extract text response
print(message.content[0].text)
```

### 7.3 Multi-Turn Conversation Helpers
```python
def add_user_message(messages, text):
    messages.append({"role": "user", "content": text})

def add_assistant_message(messages, text):
    messages.append({"role": "assistant", "content": text})

def chat(messages, system=None, temperature=1.0, stop_sequences=[], tools=None):
    params = {
        "model": model,
        "max_tokens": 1000,
        "messages": messages,
        "temperature": temperature,
        "stop_sequences": stop_sequences,
    }
    if tools:    params["tools"] = tools
    if system:   params["system"] = system
    return client.messages.create(**params)
```

### 7.4 Streaming Response
```python
with client.messages.stream(
    model=model, max_tokens=1000, messages=messages
) as stream:
    for text in stream.text_stream:
        print(text, end="")
    final_message = stream.get_final_message()  # For storage/history
```

### 7.5 Prefill + Stop Sequence (Clean JSON Output)
```python
messages = []
add_user_message(messages, "Generate an AWS EventBridge rule as JSON")
add_assistant_message(messages, "```json")  # Prefill start

text = chat(messages, stop_sequences=["```"])  # Stop before closing ```
import json
parsed_json = json.loads(text.strip())
```

### 7.6 System Prompt
```python
system_prompt = """
You are a patient math tutor.
Do not directly answer a student's questions.
Guide them to a solution step by step.
"""
answer = chat(messages, system=system_prompt)
```

### 7.7 Tool JSON Schema Definition
```python
get_current_datetime_schema = {
    "name": "get_current_datetime",
    "description": "Returns the current date and time formatted according to the specified format. "
                   "Use this when the user asks about the current time or date.",
    "input_schema": {
        "type": "object",
        "properties": {
            "date_format": {
                "type": "string",
                "description": "Python strftime format string (e.g., '%Y-%m-%d %H:%M:%S')",
                "default": "%Y-%m-%d %H:%M:%S"
            }
        },
        "required": []
    }
}
```

### 7.8 Tool Execution & Result Sending
```python
def run_tools(message):
    tool_requests = [b for b in message.content if b.type == "tool_use"]
    tool_result_blocks = []
    for req in tool_requests:
        try:
            output = run_tool(req.name, req.input)
            tool_result_blocks.append({
                "type": "tool_result",
                "tool_use_id": req.id,
                "content": json.dumps(output),
                "is_error": False
            })
        except Exception as e:
            tool_result_blocks.append({
                "type": "tool_result",
                "tool_use_id": req.id,
                "content": f"Error: {e}",
                "is_error": True
            })
    return tool_result_blocks
```

### 7.9 Structured Data Extraction via Tool
```python
article_summary_schema = {
    "name": "article_summary",
    "description": "Extracts structured data from articles",
    "input_schema": {
        "type": "object",
        "properties": {
            "title": {"type": "string"},
            "author": {"type": "string"},
            "key_insights": {"type": "array", "items": {"type": "string"}}
        }
    }
}

response = chat(
    messages,
    tools=[article_summary_schema],
    tool_choice={"type": "tool", "name": "article_summary"}  # Force specific tool
)
structured_data = response.content[0].input
```

### 7.10 Batch Tool Schema (Parallel Tool Calls)
```python
batch_tool_schema = {
    "name": "batch_tool",
    "description": "Invoke multiple other tool calls simultaneously",
    "input_schema": {
        "type": "object",
        "properties": {
            "invocations": {
                "type": "array",
                "description": "The tool calls to invoke",
                "items": {
                    "type": "object",
                    "properties": {
                        "name": {"type": "string", "description": "Tool name"},
                        "arguments": {"type": "object", "description": "Tool arguments"}
                    }
                }
            }
        }
    }
}
```

### 7.11 Web Search Tool
```python
web_search_schema = {
    "type": "web_search_20250305",
    "name": "web_search",
    "max_uses": 5,
    "allowed_domains": ["nih.gov"]  # Optional: restrict to trusted domains
}
```

### 7.12 Text Editor Tool Schema (by Model)
```python
def get_text_edit_schema(model):
    if model.startswith("claude-3-7-sonnet"):
        return {"type": "text_editor_20250124", "name": "str_replace_editor"}
    elif model.startswith("claude-3-5-sonnet"):
        return {"type": "text_editor_20241022", "name": "str_replace_editor"}
```

### 7.13 RAG — Embeddings on Vertex AI
```python
from google import genai

genai_client = genai.Client(project="YOUR_PROJECT_ID", location="global", vertexai=True)

def generate_embedding(text):
    response = genai_client.models.embed_content(
        model="text-embedding-005",
        contents=text
    )
    if not response.embeddings:
        return []
    return [e.values for e in response.embeddings]
```

### 7.14 RAG — Chunking Strategies
```python
# Size-based chunking with overlap
def chunk_by_char(text, chunk_size=150, chunk_overlap=20):
    chunks = []
    start_idx = 0
    while start_idx < len(text):
        end_idx = min(start_idx + chunk_size, len(text))
        chunks.append(text[start_idx:end_idx])
        start_idx = end_idx - chunk_overlap if end_idx < len(text) else len(text)
    return chunks

# Structure-based chunking (markdown)
def chunk_by_section(document_text):
    import re
    pattern = r'\n## '
    return re.split(pattern, document_text)

# Sentence-based chunking
def chunk_by_sentence(text, max_sentences_per_chunk=5, overlap_sentences=1):
    import re
    sentences = re.split(r'(?<=[.!?])\s+', text)
    chunks = []
    start_idx = 0
    while start_idx < len(sentences):
        end_idx = min(start_idx + max_sentences_per_chunk, len(sentences))
        chunks.append(' '.join(sentences[start_idx:end_idx]))
        start_idx += max_sentences_per_chunk - overlap_sentences
    return chunks
```

### 7.15 RAG — Vector Store + Search
```python
store = VectorIndex()
for embedding, chunk in zip(embeddings, chunks):
    store.add_vector(embedding, {"content": chunk})

# Query time
user_embedding = generate_embedding("What did the software engineering dept do?")
results = store.search(user_embedding, k=2)  # Returns (doc, cosine_distance) pairs
```

### 7.16 Hybrid Search with BM25
```python
bm25_store = BM25Index()
for chunk in chunks:
    bm25_store.add_document({"content": chunk})

# BM25 prioritizes rare terms like "INC-2023-Q4-011"
results = bm25_store.search("What happened with INC-2023-Q4-011?", k=3)
```

### 7.17 Contextual Retrieval
```python
def add_context(text_chunk, source_text):
    prompt = f"""Write a short succinct snippet to situate this chunk within the overall
source document for the purposes of improving search retrieval.

<document>
{source_text}
</document>

<chunk>
{text_chunk}
</chunk>

Answer only with the succinct context and nothing else."""

    messages = []
    add_user_message(messages, prompt)
    result = chat(messages)
    return result["text"] + "\n" + text_chunk
```

### 7.18 LLM Re-ranker
```python
def reranker_fn(docs, query_text, k):
    joined_docs = "\n".join([
        f"""<document>
<document_id>{doc["id"]}</document_id>
<document_content>{doc["content"]}</document_content>
</document>""" for doc in docs
    ])
    # Prompt Claude to return document IDs in order of relevance
    # Use tool_choice to force JSON output with document_ids array
```

### 7.19 Prompt Caching — System Prompt
```python
if system:
    params["system"] = [
        {
            "type": "text",
            "text": system,
            "cache_control": {"type": "ephemeral"}  # Mark for caching
        }
    ]
```

### 7.20 Prompt Caching — Tool Schemas
```python
if tools:
    tools_clone = tools.copy()
    last_tool = tools_clone[-1].copy()
    last_tool["cache_control"] = {"type": "ephemeral"}  # Cache up to last tool
    tools_clone[-1] = last_tool
    params["tools"] = tools_clone
```

### 7.21 Extended Thinking
```python
def chat(messages, thinking=False, thinking_budget=1024):
    params = {
        "model": model,
        "max_tokens": 4000,  # Must exceed thinking_budget
        "messages": messages
    }
    if thinking:
        params["thinking"] = {
            "type": "enabled",
            "budget": thinking_budget  # Min 1024 tokens
        }
    return client.messages.create(**params)
```

### 7.22 Vision — Image Input
```python
import base64

with open("satellite_image.png", "rb") as f:
    image_bytes = base64.standard_b64encode(f.read()).decode("utf-8")

add_user_message(messages, [
    {
        "type": "image",
        "source": {
            "type": "base64",
            "media_type": "image/png",
            "data": image_bytes
        }
    },
    {
        "type": "text",
        "text": "Analyze this image for fire risk assessment..."
    }
])
```

### 7.23 Citations — PDF Document
```python
{
    "type": "document",
    "source": {
        "type": "base64",
        "media_type": "application/pdf",
        "data": file_bytes
    },
    "title": "earth.pdf",
    "citations": {"enabled": True}
    # Returns: cited_text, document_index, document_title, start_page_number, end_page_number
}
```

### 7.24 MCP Server (FastMCP)
```python
from mcp.server.fastmcp import FastMCP
from pydantic import Field

mcp = FastMCP("DocumentMCP", log_level="ERROR")

@mcp.tool(name="read_doc_contents", description="Read the contents of a document")
def read_document(doc_id: str = Field(description="Id of the document to read")):
    if doc_id not in docs:
        raise ValueError(f"Doc with id {doc_id} not found")
    return docs[doc_id]

@mcp.tool(name="edit_document", description="Edit a document by replacing text")
def edit_document(
    doc_id: str = Field(description="Document ID"),
    old_str: str = Field(description="Text to replace (exact match including whitespace)"),
    new_str: str = Field(description="New replacement text")
):
    if doc_id not in docs:
        raise ValueError(f"Doc with id {doc_id} not found")
    docs[doc_id] = docs[doc_id].replace(old_str, new_str)
```

### 7.25 MCP Resources (Direct + Templated)
```python
@mcp.resource("docs://documents", mime_type="application/json")
def list_docs() -> list[str]:
    return list(docs.keys())  # SDK auto-serializes to JSON

@mcp.resource("docs://documents/{doc_id}", mime_type="text/plain")
def fetch_doc(doc_id: str) -> str:
    if doc_id not in docs:
        raise ValueError(f"Doc {doc_id} not found")
    return docs[doc_id]
```

### 7.26 MCP Prompt Definition
```python
from mcp.server.fastmcp.prompts import base

@mcp.prompt(name="format", description="Rewrites a document in Markdown format")
def format_document(doc_id: str = Field(description="Id of document to format")) -> list[base.Message]:
    prompt = f"""Your goal is to reformat a document to use markdown syntax.
The id of the document is: {doc_id}
Add headers, bullet points, tables, etc as necessary.
Use the 'edit_document' tool to edit the document."""
    return [base.UserMessage(prompt)]
```

### 7.27 MCP Client Functions
```python
async def list_tools(self) -> list:
    result = await self.session().list_tools()
    return result.tools

async def call_tool(self, tool_name: str, tool_input: dict):
    return await self.session().call_tool(tool_name, tool_input)

async def read_resource(self, uri: str):
    from pydantic import AnyUrl
    import json
    result = await self.session().read_resource(AnyUrl(uri))
    resource = result.contents[0]
    if resource.mimeType == "application/json":
        return json.loads(resource.text)
    return resource.text
```

### 7.28 gcloud CLI Setup Commands
```bash
gcloud init
gcloud auth login
gcloud config set project YOUR_PROJECT_ID
gcloud auth application-default login
pip install "anthropic[vertex]"
pip install google-genai
mcp dev mcp_server.py    # Start MCP inspector (port 6277)
```

---

## 8. Real-World Use Cases

### 8.1 Fire Risk Assessment (Vision API)
- **Problem:** Physical inspection of every insured property is expensive
- **Solution:** Upload satellite images; Claude analyzes tree overhang, defensible space, fuel ladders
- **Prompt pattern:** Detailed 5-step analysis framework with criteria for ratings 1-4
- **Output:** Standardized fire risk rating (1=Low, 2=Moderate, 3=High, 4=Severe)
- **Impact:** Eliminates need for physical property inspectors at scale

### 8.2 AWS Code Generator with Prompt Evaluations
- **Problem:** Claude adds verbose explanations around code; unreliable format
- **Solution:** Prefill + stop sequences; code grader (syntax) + model grader (quality)
- **Iterative improvement:** Baseline score 6.2 → refined score 8.7 using systematic evals
- **Impact:** Reliable, production-ready Python/JSON/Regex generation for AWS tasks

### 8.3 Document Management Chatbot (MCP)
- **Problem:** Building GitHub/document integrations requires writing hundreds of tool schemas
- **Solution:** MCP Server exposes `read_doc_contents` + `edit_document` tools; MCP Client connects users
- **Impact:** Natural language document management without manual API integration overhead

### 8.4 Reminder System (Multi-Tool Agent)
- **Tools:** `get_current_datetime`, `add_duration_to_datetime`, `set_reminder`
- **Request:** "Set a reminder for my appointment — 177 days after Jan 1, 2050"
- **Claude chaining:** Calculate date → Set reminder (2 sequential tool calls)
- **Impact:** Natural language → structured scheduling automatically

### 8.5 Financial Document Q&A (RAG)
- **Problem:** 800-page financial report exceeds context window; expensive if included in full
- **Solution:** Chunk → embed → vector store → semantic search → citations-enabled answer
- **Impact:** Accurate, citation-backed responses from massive documents at low cost

### 8.6 Social Media Video Creator (Agent + Parallelization)
- **Tools:** bash (FFmpeg), generate_image, text_to_speech, post_media
- **Collaborative mode:** User approves image → Claude proceeds with video creation
- **Impact:** Fully autonomous or semi-autonomous content pipeline

### 8.7 Image to CAD (Evaluator-Optimizer Workflow)
- **Flow:** Image → describe → CadQuery code → 3D render → grade → (if rejected) → feedback → repeat
- **Pattern:** Classic evaluator-optimizer with automatic refinement loop
- **Impact:** Automated CAD generation from photographs with quality assurance

### 8.8 Material Designer (Parallelization Workflow)
- **Problem:** Single prompt asking Claude to analyze 6+ material types simultaneously is overwhelming
- **Solution:** Run 6 parallel requests (one per material) → aggregate results → final recommendation
- **Impact:** Better accuracy, independent prompt optimization per material, faster execution

---

## 9. Interview Questions & Answers

### Q1: Why must you never make Claude API requests from client-side code?
**A:** API credentials (API keys) must remain secret. Client-side JavaScript is visible to anyone who inspects browser traffic or network requests. A leaked API key gives unauthorized access to your account. Always route through a **server you control and secure**.

---

### Q2: Explain Claude's text generation process step by step.
**A:** Four stages:
1. **Tokenization** — breaks input into tokens (words, sub-words, punctuation)
2. **Embedding** — converts each token to a numerical vector capturing all possible meanings
3. **Contextualization** — adjusts each embedding based on surrounding tokens (attention mechanism) to resolve ambiguity
4. **Generation** — computes probability distribution for next token; uses temperature to sample; repeats until stop condition

---

### Q3: Why do you need to send full conversation history with every request?
**A:** Claude is **completely stateless** — each API call is independent with no memory of prior calls. To maintain conversational context (so Claude knows what "Write another sentence" refers to), you must include all previous `user` and `assistant` messages in every request. This is the developer's responsibility, not the API's.

---

### Q4: What is the difference between `max_tokens` and temperature?
**A:**
- `max_tokens` = **budget ceiling** for response length. Claude won't exceed it but doesn't try to reach it.
- `temperature` = **creativity/randomness dial** (0.0 = always picks highest-probability token = deterministic; 1.0 = distributes probability = creative/varied)

---

### Q5: How does streaming improve user experience and how do you implement it?
**A:** Without streaming, users wait 10–30 seconds in silence. With streaming, text appears word-by-word creating a real-time typing effect. Implementation:
```python
with client.messages.stream(...) as stream:
    for text in stream.text_stream:
        print(text, end="")  # Forward to client via WebSocket/SSE in production
    final_message = stream.get_final_message()  # For storage
```

---

### Q6: What is prompt prefilling and why is it useful?
**A:** You add an `assistant` message at the END of the messages list. Claude sees this and continues from that point. Key uses:
- **Force JSON output:** Prefill `"` + stop sequence `"` → get clean JSON only
- **Steer bias:** Prefill `"Coffee is better because"` → Claude argues for coffee
- Claude NEVER repeats the prefilled text — it picks up right after it

---

### Q7: Describe the complete tool use cycle.
**A:**
1. Send user message + tool schemas to Claude
2. Claude responds with `stop_reason = "tool_use"` + `ToolUseBlock` (tool name + input params)
3. Your code executes the tool function
4. Send back a `tool_result` block with the same `tool_use_id`
5. Claude responds with final text answer
6. Repeat if Claude requests more tools (loop until `stop_reason != "tool_use"`)

---

### Q8: Explain Retrieval Augmented Generation (RAG) and when you'd use it.
**A:** RAG breaks large documents into chunks, generates numerical embeddings, stores them in a vector database, then at query time retrieves the most semantically similar chunks to include in the Claude prompt — instead of the entire document.

**Use when:**
- Documents exceed context window limits
- You need cost-efficient answers from large document collections
- You want to search across multiple documents

---

### Q9: What is BM25 and why combine it with semantic search?
**A:** BM25 (Best Match 25) is a **lexical search algorithm** that weights search terms by their rarity across the document corpus — rare terms like "INC-2023-Q4-011" get higher scores. Semantic search alone may miss these exact matches because it focuses on meaning.

**Hybrid approach:** Run both in parallel → merge results using **Reciprocal Rank Fusion (RRF)** → better accuracy than either method alone.

---

### Q10: Explain the difference between MCP Tools, Resources, and Prompts.
**A:**
- **Tools** — controlled by **Claude** autonomously during conversation (file operations, API calls)
- **Resources** — controlled by **your application code** (fetching UI data, populating autocomplete)
- **Prompts** — controlled by **users** via explicit actions (slash commands, buttons, predefined workflows)

---

### Q11: What is prompt caching and what are its key limitations?
**A:** Prompt caching saves Claude's preprocessing work (tokenization, embedding, contextualization) so subsequent identical requests skip reprocessing.
- **Benefit:** Faster responses + lower costs
- **Limitations:**
  - Cache expires after **5 minutes**
  - Needs **1024+ tokens** to cache
  - Content must be **byte-for-byte identical** — even one character change invalidates the cache

---

### Q12: What is Extended Thinking and when should you use it?
**A:** Extended thinking allocates a "thinking budget" (tokens) for Claude to reason through complex problems before answering. You see the thinking content (with a cryptographic signature preventing tampering).

**When to use:** Only after exhausting standard prompt optimization. Trade-offs: higher token cost + increased latency.

**Minimum budget:** 1024 tokens. `max_tokens` must exceed `thinking_budget`.

---

### Q13: What is Contextual Retrieval and how does it improve RAG accuracy?
**A:** Standard chunking strips chunks from their document context. Contextual retrieval uses Claude to write a short "situating snippet" for each chunk (describing where it sits in the document). This snippet is prepended to the chunk before indexing, giving the retrieval system more context to find the right chunks.

---

### Q14: When would you use a Workflow vs. an Agent?
**A:**
- **Workflow** — when you can predict the exact steps in advance. More reliable, testable, predictable cost. Example: Image → CAD generation pipeline.
- **Agent** — when the task path is unknown; give Claude abstract tools and let it decide. More flexible, handles diverse requests, but harder to evaluate and less predictable. Example: General coding assistant.

---

### Q15: What are the key workflow patterns and their use cases?
**A:**
| Pattern | Use Case |
|---------|---------|
| **Chaining** | Sequential subtasks where output of step N feeds step N+1 |
| **Parallelization** | Independent subtasks run simultaneously + aggregate (e.g., material analysis) |
| **Routing** | Categorize input first → route to specialized pipeline (e.g., educational vs entertainment content) |
| **Evaluator-Optimizer** | Producer generates → Grader evaluates → Feedback loop until quality threshold met |

---

## 10. Certification Questions

### CQ1: Which parameter controls the maximum number of tokens Claude can generate?
- A) `temperature`
- **B) `max_tokens`** ✅
- C) `stop_sequences`
- D) `thinking_budget`

**Explanation:** `max_tokens` sets the ceiling on generated response length — Claude won't exceed it but doesn't try to reach it.

---

### CQ2: What value does `stop_reason` take when Claude wants to call a tool?
- A) `"end_turn"`
- **B) `"tool_use"`** ✅
- C) `"max_tokens"`
- D) `"stop_sequence"`

**Explanation:** `stop_reason == "tool_use"` signals that Claude needs tool execution before generating the final response.

---

### CQ3: How long does a prompt cache entry remain valid?
- A) 1 minute
- **B) 5 minutes** ✅
- C) 60 minutes
- D) Until the session ends

**Explanation:** Cache entries expire after 5 minutes. This is why caching is most valuable for tool schemas and system prompts that stay stable across rapid, repeated requests.

---

### CQ4: What is the minimum `thinking_budget` for Extended Thinking?
- A) 256 tokens
- B) 512 tokens
- **C) 1024 tokens** ✅
- D) 2048 tokens

---

### CQ5: Which RAG chunking strategy works best for documents of unknown format?
- A) Semantic-based
- B) Structure-based
- **C) Size-based** ✅
- D) BM25-based

**Explanation:** Size-based chunking works reliably regardless of document format. Add overlap to preserve context at chunk boundaries.

---

### CQ6: What does a cosine similarity of 0.0 between two embeddings indicate?
- A) The vectors are identical
- **B) The vectors are unrelated (perpendicular)** ✅
- C) The vectors are opposites
- D) One vector is empty

**Explanation:** 1.0 = identical, 0.0 = unrelated/perpendicular, -1.0 = completely opposite.

---

### CQ7: In MCP, which primitive is controlled by the AI model (Claude)?
- A) Resources
- B) Prompts
- **C) Tools** ✅
- D) Templates

**Explanation:** Claude autonomously decides when to call Tools. Resources = app-controlled, Prompts = user-controlled.

---

### CQ8: Which `tool_choice` value forces Claude to use a specific named tool?
- A) `{"type": "auto"}`
- B) `{"type": "any"}`
- **C) `{"type": "tool", "name": "TOOL_NAME"}`** ✅
- D) `{"type": "force"}`

---

### CQ9: What algorithm is used to merge results from semantic search and BM25?
- A) Cosine Similarity Averaging
- **B) Reciprocal Rank Fusion (RRF)** ✅
- C) BM25 Override
- D) Vector Normalization

**Explanation:** RRF uses rank positions (not raw scores) to combine results from multiple search methods into a unified ranking.

---

### CQ10: In the Evaluator-Optimizer workflow pattern, what happens when the grader rejects output?
- A) The workflow terminates with an error
- B) Output is returned to user with a warning
- **C) Feedback is sent back to the producer for another attempt** ✅
- D) A human grader is automatically invoked

---

## 11. Revision Notes

### Quick Reference — Prompt Engineering Techniques

| Technique | Purpose | Measured Impact |
|-----------|---------|----------------|
| **Clear & direct first line** | Unambiguous task definition | Score 2.3 → 3.9 |
| **Quality guidelines** | Specify length, structure, content requirements | Score 3.9 → 7.9 |
| **XML tags** | Separate instructions from data clearly | Prevents parsing confusion |
| **Multi-shot examples** | Show ideal input/output pairs | Handles edge cases |
| **Process steps** | Guide reasoning through complex decisions | Better complex task accuracy |
| **System prompts** | Define persona/role consistently | Consistent behavior |
| **Temperature control** | Tune creativity vs. determinism | Task-appropriate output |
| **Prefill + Stop sequences** | Extract clean structured output | Precise format control |

---

### Quick Reference — Text Editor Tool Operations

| Operation | What Claude Does |
|-----------|-----------------|
| **View** | Read file or directory contents |
| **View range** | Read specific line numbers in a file |
| **Replace** | Find and replace text in file |
| **Create** | Create a new file |
| **Insert** | Insert text at a specific line |
| **Undo** | Revert the most recent edit |

---

### Quick Reference — Response Block Types

| Block Type | When It Appears |
|-----------|----------------|
| `TextBlock` | Always — Claude's text response |
| `ToolUseBlock` | When Claude decides to call a tool |
| `ThinkingBlock` | When extended thinking is enabled |
| `WebSearchToolResultBlock` | When web search tool is active |
| `CitationCharLocation` | When citations enabled on plain text documents |

---

### Quick Reference — Prompt Caching Order
```
Tools (add cache_control to LAST tool)
    --> System Prompt (add cache_control to text block)
        --> Messages (add cache_control to specific message block)

Max 4 cache breakpoints total per request
Minimum 1024 tokens required for caching
Cache lifetime: 5 minutes
```

---

### Quick Reference — Vision Limits Summary
```
Max 100 images per request
Max 5MB per image
Single image:    max 8000px width/height
Multiple images: max 2000px width/height
Token cost = (width_px x height_px) / 750
Input formats: base64 OR URL
```

---

## 12. One-Page Summary

```
============================================================
  CLAUDE ON GOOGLE CLOUD VERTEX AI — QUICK REVISION CHEAT SHEET
============================================================

SETUP:
  gcloud auth application-default login
  pip install "anthropic[vertex]"
  client = AnthropicVertex(region="global", project_id="YOUR_ID")

CORE RULES:
  [1] Always use server-side API calls — NEVER client-side
  [2] Always send FULL conversation history (Claude is stateless)
  [3] max_tokens = ceiling (NOT target); temperature = creativity (0-1)
  [4] stop_reason == "tool_use" --> execute tool --> send tool_result
  [5] Cache: 5 min | 1024+ tokens | byte-identical content

TEXT GENERATION:
  Tokenization --> Embedding --> Contextualization --> Generation

KEY PARAMETERS:
  system          = Claude role/persona
  temperature     = 0 (factual) to 1 (creative)
  max_tokens      = response length budget
  stop_sequences  = halt generation at string
  stream=True     = chunk-by-chunk response
  tools           = available function schemas
  tool_choice     = auto | any | {type:"tool", name:"X"}

RAG PIPELINE:
  Chunk --> Embed (text-embedding-005) --> Vector DB
  Query --> Embed --> Search (Semantic + BM25) --> RRF --> Rerank --> Claude

MCP PRIMITIVES:
  Tools     = Claude-controlled (give Claude capabilities)
  Resources = App-controlled   (fetch data for UI/context)
  Prompts   = User-controlled  (pre-built prompt templates)

WORKFLOW PATTERNS:
  Chaining         = sequential subtasks
  Parallelization  = simultaneous specialized subtasks --> aggregate
  Routing          = categorize --> specialized pipeline
  Evaluator-Opt    = producer --> grader --> feedback loop

CHUNKING STRATEGIES:
  Size-based      = universal, add overlap for context
  Structure-based = markdown/structured docs
  Semantic-based  = NLP grouping, most accurate, expensive

EXTENDED THINKING:
  Min budget: 1024 tokens | max_tokens must exceed budget
  Response: thinking block (signed) + text block

IMAGE LIMITS:
  100 images max | 5MB each | 8000px (single) / 2000px (multi)
  Token cost = (W x H) / 750

MCP SETUP:
  mcp dev mcp_server.py   --> browser inspector port 6277
  @mcp.tool()  @mcp.resource()  @mcp.prompt() decorators
============================================================
```

---

## 13. Top 20 Key Takeaways

1. **Never expose API credentials** in client-side code; always use a server intermediary
2. **Claude is stateless** — send complete conversation history with every API call
3. **Text generation = 4 stages:** Tokenize → Embed → Contextualize → Generate (with temperature sampling)
4. **Temperature 0 = deterministic, Temperature 1 = creative** — match to your use case
5. **Streaming** improves UX dramatically — use `stream.text_stream` for word-by-word output
6. **Prefill + Stop sequences = precise structured output** extraction (clean JSON, code blocks)
7. **Tool use loop:** `stop_reason == "tool_use"` → execute → send `tool_result` → repeat until done
8. **`tool_choice: {type: "tool", name: "X"}`** forces Claude to use a specific tool for structured data
9. **Prompt evaluation = objective improvement** — score, iterate, measure, don't guess
10. **RAG = chunk → embed → vector store → semantic search → include relevant chunks** (not full doc)
11. **BM25 + Semantic = Hybrid search** merged via RRF — better than either method alone
12. **Contextual retrieval** adds situating snippets to chunks before indexing — improves retrieval accuracy
13. **MCP removes the burden** of writing tool schemas/implementations — use pre-built MCP Servers
14. **MCP Primitives:** Tools (Claude), Resources (App), Prompts (User) — different controllers, different purposes
15. **Prompt caching** = 5-min cache, 1024-token minimum, byte-identical content — saves cost + time
16. **Extended thinking** = last resort after prompt optimization — higher cost, higher accuracy
17. **Vision requires structured prompts** (step-by-step methodology) for accurate results
18. **Citations** make Claude transparent — shows exact source pages/characters used in responses
19. **Agents** = flexible but less reliable; **Workflows** = reliable but rigid — choose by task certainty
20. **Abstract tools make better agents** — Claude combines generic tools creatively (bash, grep, read, write)

---

## 14. Most Important Topics to Revise

### Tier 1 — Must Know (Highest Exam Probability)
- [ ] 5-step request lifecycle (Client → Server → Vertex → Model → Response)
- [ ] Claude text generation stages (Tokenize, Embed, Contextualize, Generate)
- [ ] Stateless conversation management (why needed + how implemented)
- [ ] Tool use flow: detect `tool_use` → execute → send `tool_result` → loop
- [ ] Prompt caching rules: 5 min, 1024 tokens, identical content, ordering
- [ ] RAG pipeline: 5-step flow from chunking to final prompt
- [ ] MCP primitives: Tools/Resources/Prompts and their controllers
- [ ] Workflows vs Agents: when to use each

### Tier 2 — Should Know (Medium Exam Probability)
- [ ] Temperature ranges and use cases table
- [ ] Streaming event types and production patterns
- [ ] Prefill + stop sequence for structured output extraction
- [ ] Chunking strategies comparison (size/structure/semantic)
- [ ] BM25 + Semantic hybrid search + RRF formula
- [ ] Extended thinking requirements (1024 min, max_tokens > budget)
- [ ] Vision API limits (100 images, 5MB, 8000px/2000px, token formula)
- [ ] `tool_choice` parameter options and when to use each

### Tier 3 — Good to Know (Lower Exam Probability)
- [ ] Contextual retrieval implementation
- [ ] Re-ranking with LLM prompt structure
- [ ] Workflow patterns (Chaining, Parallelization, Routing, Evaluator-Optimizer)
- [ ] Batch tool schema for parallel tool calls
- [ ] Citations structure (CharLocation vs page numbers for PDF)
- [ ] MCP transport types (stdio, HTTP, WebSockets)
- [ ] FastMCP decorator syntax + MCP Inspector usage

---

## 15. Certification Preparation Roadmap

```
WEEK 1 — Foundation
  [x] Complete request lifecycle (5 steps)
  [x] Vertex AI setup (gcloud CLI + SDK install + Model Garden)
  [x] Basic API calls with AnthropicVertex client
  [x] Multi-turn conversation management (stateless pattern)
  [x] Temperature, system prompts, max_tokens

WEEK 2 — Prompt Engineering
  [x] Prompt engineering techniques (clear+direct, guidelines, XML, multi-shot)
  [x] Prefill + stop sequences for structured output
  [x] Prompt evaluation pipeline (5 steps: draft → dataset → run → grade → iterate)
  [x] Code graders (syntax) + model graders (quality)
  [x] Building and using evaluation datasets

WEEK 3 — Tool Use & MCP
  [x] Custom tool functions + JSON schema definitions
  [x] Multi-turn tool conversation loop (run_conversation pattern)
  [x] Batch tool pattern for parallel tool calls
  [x] Structured data extraction via tool_choice
  [x] Built-in tools: Text Editor + Web Search
  [x] MCP architecture: primitives, FastMCP server, MCP client, inspector

WEEK 4 — Advanced Features
  [x] RAG pipeline (chunk → embed → store → search → prompt)
  [x] Hybrid search (BM25 + Semantic + RRF merge)
  [x] Contextual retrieval + LLM re-ranking
  [x] Prompt caching (system, tools, messages)
  [x] Extended thinking (budget, signatures, redacted blocks)
  [x] Vision API (limits, token formula, structured prompts)
  [x] Citations (PDF + plain text, CharLocation)

WEEK 5 — Architecture & Patterns
  [x] Workflows vs Agents (reliability, flexibility, cost comparison)
  [x] Chaining workflow pattern + constraint handling
  [x] Parallelization workflow + aggregation
  [x] Routing workflow (categorize → specialize)
  [x] Evaluator-Optimizer pattern
  [x] Environment inspection in agents

WEEK 6 — Exam Preparation
  [x] Review all 10 Certification Questions (Section 10)
  [x] Review all 15 Interview Questions (Section 9)
  [x] Memorize One-Page Summary cheat sheet (Section 12)
  [x] Review Top 20 Key Takeaways (Section 13)
  [x] Build one mini end-to-end project (RAG or Tool-use)
  [x] Practice timed mock questions
```

---

*Notes prepared from: Claude with Google Cloud's Vertex AI course material + 4 reference images*
*Images analyzed: img1 (API flow diagram), img2 (Vertex AI Model Garden nav), img3 (Anthropic models list), img4 (Claude Sonnet 4 Enable button)*
*Created: June 2026*
*File: E:\Teja_Interview_preparation\Claude_Preparation\Claude_Architect_Preparation\Claude_with_Google_Vertex_AI.md*
