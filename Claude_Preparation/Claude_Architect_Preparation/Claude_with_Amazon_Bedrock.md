# 📘 Claude with Amazon Bedrock — Certification & Interview Preparation Notes

> **Role**: Expert AI Trainer | Certification Mentor | Technical Content Architect
> **Scope**: AWS Bedrock, Claude Models, MCP, Tool Use, RAG, Extended Thinking, Prompt Evaluation
> **Target**: Certification Preparation · Interview Readiness · Architect-Level Understanding

---

## 📋 Table of Contents

1. [Module Overview & Learning Objectives](#1-module-overview--learning-objectives)
2. [Core Concepts & Definitions](#2-core-concepts--definitions)
3. [Key Tools & Technologies](#3-key-tools--technologies)
4. [AWS Bedrock Architecture & Workflow](#4-aws-bedrock-architecture--workflow)
5. [Claude Model Families on Bedrock](#5-claude-model-families-on-bedrock)
6. [Bedrock API Deep Dive](#6-bedrock-api-deep-dive)
7. [Context & Message Management](#7-context--message-management)
8. [Tool Use (Function Calling)](#8-tool-use-function-calling)
9. [Retrieval-Augmented Generation (RAG)](#9-retrieval-augmented-generation-rag)
10. [Model Context Protocol (MCP)](#10-model-context-protocol-mcp)
11. [Advanced Features](#11-advanced-features)
12. [Project Architecture: Spring Boot + Bedrock](#12-project-architecture-spring-boot--bedrock)
13. [Security, IAM & Deployment](#13-security-iam--deployment)
14. [Prompt Evaluation & Testing Framework](#14-prompt-evaluation--testing-framework)
15. [Certification-Relevant Topics ⭐](#15-certification-relevant-topics-)
16. [Code Examples & Patterns](#16-code-examples--patterns)
17. [Interview Q&A](#17-interview-qa)
18. [Certification Practice Q&A](#18-certification-practice-qa)
19. [Revision Quick Notes](#19-revision-quick-notes)
20. [One-Page Summary](#20-one-page-summary)
21. [Top Key Takeaways](#21-top-key-takeaways)

---

## 1. Module Overview & Learning Objectives

### What This Module Covers
This module covers **production-grade integration of Claude AI models via AWS Bedrock**, spanning:
- AWS Bedrock service internals and API usage
- Claude model selection and inference configuration
- Building multi-turn conversations with full message management
- Tool use / function calling for real-world data access
- RAG pipelines for large-document Q&A
- Model Context Protocol (MCP) for pre-built tool integrations
- Prompt evaluation and automated testing
- Spring Boot + JavaScript microservice architecture

### Learning Objectives
After mastering this material, you will be able to:
- ✅ Explain how AWS Bedrock routes requests to foundation models
- ✅ Configure and call Claude models using `boto3` and the Converse API
- ✅ Build stateful multi-turn conversations with message history management
- ✅ Implement tool use (function calling) with JSON Schema definitions
- ✅ Design and implement RAG pipelines with hybrid search
- ✅ Integrate MCP servers to extend Claude's capabilities without writing tool code
- ✅ Apply prompt evaluation techniques to compare and improve prompts
- ✅ Deploy Bedrock-backed applications securely on AWS (EC2, ECS, Lambda)

---

## 2. Core Concepts & Definitions

| Term | Definition |
|------|-----------|
| **AWS Bedrock** | Fully managed AWS service providing access to foundation models (FMs) from Anthropic, Meta, Amazon, Mistral, Cohere, and others through a single unified API. No GPU management required. |
| **Foundation Model (FM)** | A large, pre-trained AI model that can be adapted to many tasks. Examples: Claude 3.5 Sonnet, Amazon Titan, Meta Llama 3. |
| **Converse API** | Bedrock's unified, model-agnostic chat API. Works with ALL Bedrock-supported models using the same code structure. |
| **InvokeModel API** | Low-level Bedrock API requiring model-specific JSON payloads. Use only when Converse API lacks a needed feature. |
| **Inference Profile** | A Bedrock construct that abstracts regional model availability. Automatically routes requests to a region where the model is hosted. |
| **SigV4 Authentication** | AWS Signature Version 4 — the standard signing protocol used for all AWS API requests, including Bedrock. |
| **Tool Use (Function Calling)** | Mechanism by which Claude requests your server to execute external functions (tools) and return data it cannot access internally. |
| **RAG** | Retrieval-Augmented Generation — technique of chunking documents, embedding chunks, storing in a vector DB, then injecting relevant chunks into prompts. |
| **MCP (Model Context Protocol)** | Anthropic-created protocol allowing AI models to access external data and tools through standardized server interfaces, eliminating custom integration code. |
| **Embedding** | A numerical vector representation of text that captures semantic meaning, enabling similarity search. |
| **Vector Database** | Storage for embeddings that supports cosine similarity search (e.g., comparing user query embedding vs. stored chunk embeddings). |
| **BM25** | Best Match 25 — a lexical/keyword-based ranking algorithm used alongside semantic search in hybrid RAG pipelines. |
| **Prompt Caching** | Feature that stores Claude's preprocessing work (tokenization, embeddings) for 5 minutes, making follow-up requests cheaper and faster. |
| **Extended Thinking** | Feature that gives Claude a budget of "reasoning tokens" to think through complex problems before generating its final response. |
| **Temperature** | Parameter (0.0–1.0) controlling randomness in token selection. 0.0 = deterministic, 1.0 = maximum diversity. |
| **Stop Sequences** | Custom strings that force Claude to stop generating output when encountered. Used to control output format. |
| **Contextual Retrieval** | Enhancement where Claude adds situating context to each RAG chunk before storage, improving search accuracy. |
| **Reciprocal Rank Fusion (RRF)** | Algorithm to merge ranked result lists from different search methods by combining position-based scores. Formula: `RRF(d) = Σ(1/(k + rank_i(d)))`. |

---

## 3. Key Tools & Technologies

### AWS Services
| Service | Purpose in Bedrock Context |
|---------|---------------------------|
| **Amazon Bedrock** | Core FM hosting and inference service |
| **IAM (Identity & Access Management)** | Authentication, authorization, credential management |
| **AWS SDK (boto3 / AWS SDK v2)** | Client library for programmatic access to Bedrock |
| **CloudWatch** | Monitoring, logging, usage metrics for Bedrock calls |
| **ECS (Elastic Container Service)** | Recommended production container orchestration |
| **EC2** | Virtual machine-based deployment option |
| **Lambda** | Serverless deployment (limited for long Bedrock calls) |
| **ECR (Elastic Container Registry)** | Docker image storage for ECS deployments |

### Programming Libraries & Frameworks
| Library | Language | Purpose |
|---------|----------|---------|
| **boto3** | Python | AWS SDK — used to instantiate `bedrock-runtime` client |
| **AWS SDK v2 (Java)** | Java | `BedrockRuntimeClient` bean in Spring Boot |
| **Spring Boot** | Java | REST API microservice backend |
| **Spring Data JPA** | Java | Database ORM layer for MySQL |
| **Bucket4j** | Java | In-process rate limiting (token bucket algorithm) |
| **Express.js** | JavaScript/Node.js | Lightweight proxy microservice (JS client) |
| **Axios** | JavaScript | HTTP client for JS microservice to call Spring Boot API |
| **concurrent.futures** | Python | Thread pool for parallel dataset generation in evaluation |

### AI / ML Concepts
| Concept | Tool/Framework |
|---------|---------------|
| Embedding generation | Amazon Titan Embeddings V2 |
| Vector similarity search | Custom `VectorIndex` class (cosine similarity) |
| Lexical search | `BM25Index` (Best Match 25) |
| Hybrid search fusion | Reciprocal Rank Fusion (RRF) |
| Re-ranking | Claude as LLM-based re-ranker |

---

## 4. AWS Bedrock Architecture & Workflow

### Internal Architecture
```
YOUR APPLICATION (Spring Boot + AWS SDK v2)
        │ HTTPS (TLS 1.2+) + SigV4 Authentication
        ▼
┌───────────────────────────────────────┐
│           AWS BEDROCK SERVICE          │
│  API Gateway → IAM Auth → Model Router │
│                   │                   │
│     ┌─────────────┼─────────────┐     │
│     ▼             ▼             ▼     │
│  Claude 3.5   Amazon Titan  Meta Llama│
│  (Anthropic)  (AWS-native)  (AWS-run) │
└───────────────────────────────────────┘
```

### ⭐ Step-by-Step API Call Flow
1. **Your App** sends HTTPS request with SigV4 signature
2. **API Gateway** receives request, validates TLS
3. **IAM Authentication** verifies credentials and permissions
4. **Model Router** identifies target model from `modelId`
5. **Inference Engine** loads model (pre-warmed), processes prompt
6. **Token Generation** — model generates tokens (or streams them)
7. **Response** sent back with generated text + usage metadata

### Key Properties of Bedrock
- ✅ Models run in **AWS infrastructure** (not the model provider's)
- ✅ Your data **never leaves AWS** (stays in your region)
- ✅ **No model training on your data**
- ✅ **Pay-per-token** (no upfront commitment)
- ✅ **Serverless** — auto-scales with demand

### ⭐ Inference Profiles (Cross-Region Routing)
- **Problem**: Not every model is available in every AWS region. Using the wrong region → cryptic "model doesn't exist" error.
- **Solution**: Use **Inference Profile IDs** (found under "Cross-region inference" in Bedrock console). These automatically route requests to a region where the model is actually hosted.
- **Difference from Model IDs**: Inference profiles abstract away regional availability; standard model IDs require you to be in a region where the model exists.

---

## 5. Claude Model Families on Bedrock

### Model Tiers & Use Cases

| Model | Speed | Cost | Best For |
|-------|-------|------|---------|
| **Claude 3.5 Sonnet v2** | Fast | Medium | ⭐ Code generation, complex chat, analysis |
| **Claude 3.5 Haiku** | Fastest | Lowest | High-volume, cost-sensitive applications |
| **Claude 3 Opus** | Slow | Highest | Most complex reasoning tasks |
| **Claude 3 Haiku** | Fast | Low | Simple Q&A, lightweight processing |

### Model Selection Guidelines
- **Coding tasks** → Claude 3.5 Sonnet v2 (best code generation & debugging)
- **General chat** → Claude 3.5 Sonnet v2 or Haiku (balance of quality/cost)
- **Embeddings (RAG, search)** → Amazon Titan Embeddings V2 (AWS-native, cheapest)
- **Multilingual** → Mistral Large
- **Budget-friendly alternative** → Llama 3.1 70B

### Pricing Reference (per 1M tokens)
| Model | Input | Output | ~Cost/1000 calls* |
|-------|-------|--------|------------------|
| Claude 3 Haiku | $0.25 | $1.25 | ~$0.15 |
| Claude 3.5 Haiku | $0.80 | $4.00 | ~$0.48 |
| Claude 3.5 Sonnet | $3.00 | $15.00 | ~$1.80 |
| Claude 3 Opus | $15.00 | $75.00 | ~$9.00 |
| Titan Text Lite | $0.15 | $0.20 | ~$0.04 |
*Estimated 100 input + 200 output tokens per call

---

## 6. Bedrock API Deep Dive

### ⭐ Converse API vs. InvokeModel API
```
OLD WAY — InvokeModel (different JSON per model):
  Claude:  { "anthropic_version": "...", "messages": [...] }
  Titan:   { "inputText": "...", "textGenerationConfig": {...} }
  Llama:   { "prompt": "...", "max_gen_len": 512 }

NEW WAY — Converse API (SAME code for ALL models):
  ConverseRequest.builder()
    .modelId("ANY-MODEL-ID-HERE")
    .messages(userMessage)
    .inferenceConfig(config)
    .build();
```

### ⭐ Converse API Request Structure (Python/boto3)
```python
import boto3

client = boto3.client("bedrock-runtime", region_name="us-west-2")

response = client.converse(
    modelId="us.anthropic.claude-3-5-sonnet-20241022-v2:0",  # inference profile
    messages=[
        {
            "role": "user",
            "content": [{"text": "Explain AWS Bedrock"}]
        }
    ],
    system=[{"text": "You are a cloud computing expert."}],
    inferenceConfig={
        "maxTokens": 4096,
        "temperature": 0.7,
        "topP": 0.9,
        "stopSequences": ["END"]
    }
)

# Extracting response
text = response["output"]["message"]["content"][0]["text"]
input_tokens = response["usage"]["inputTokens"]
output_tokens = response["usage"]["outputTokens"]
stop_reason = response["stopReason"]  # "end_turn", "max_tokens", "stop_sequence"
```

### Converse API Response Structure (Java / Spring Boot)
```java
ConverseResponse response = client.converse(request);
String text    = response.output().message().content().get(0).text();
int inputTok   = response.usage().inputTokens();
int outputTok  = response.usage().outputTokens();
String stop    = response.stopReasonAsString();
// stopReason values: "end_turn", "max_tokens", "tool_use", "stop_sequence"
```

### ⭐ Key Inference Parameters
| Parameter | Range | Description |
|-----------|-------|-------------|
| `temperature` | 0.0 – 1.0 | Token selection randomness. 0 = deterministic, 1 = maximum diversity. |
| `topP` | 0.0 – 1.0 | Nucleus sampling. Only tokens in top P probability mass are considered. |
| `maxTokens` | 1 – model max | Maximum output tokens to generate. |
| `stopSequences` | list of strings | Force model to stop when any sequence is encountered. |

### ⭐ Stop Reasons — What They Mean
| `stopReason` | Meaning | Action Required |
|-------------|---------|-----------------|
| `end_turn` | Model finished naturally | Display response to user |
| `max_tokens` | Hit token limit; response may be truncated | Increase `maxTokens` or chunk input |
| `tool_use` | Model wants to call a tool | Extract tool use parts, run functions, send results back |
| `stop_sequence` | Encountered a custom stop sequence | Parse output up to that point |

---

## 7. Context & Message Management

### ⭐ Critical Rule: No Native State in Bedrock
**AWS Bedrock does NOT maintain conversation state.** Every API call is stateless. You must:
1. Maintain a message list in your application code
2. Pass the **complete conversation history** with every request
3. Manage the `user → assistant → user → assistant` alternation pattern

### Message List Structure
```python
messages = []

def add_user_message(messages, content):
    if isinstance(content, str):
        messages.append({"role": "user", "content": [{"text": content}]})
    else:
        messages.append({"role": "user", "content": content})  # for multi-part

def add_assistant_message(messages, content):
    if isinstance(content, str):
        messages.append({"role": "assistant", "content": [{"text": content}]})
    else:
        messages.append({"role": "assistant", "content": content})
```

### Multi-Part Message Content
Messages can contain multiple content parts (text, images, documents, tool use, tool results):
```python
# Multi-part user message (text + image)
add_user_message(messages, [
    {"image": {"format": "png", "source": {"bytes": image_bytes}}},
    {"text": "What do you see in this image?"}
])
```

### ⭐ Message Alternation Rule
- Messages MUST alternate: `user → assistant → user → assistant`
- You cannot have two consecutive `user` or `assistant` messages
- Tool results are sent as `user` role messages

### Streaming with ConverseStream
```python
# Use ConverseStream for real-time token-by-token output
response = client.converse_stream(
    modelId=model_id,
    messages=messages,
    ...
)
for event in response["stream"]:
    if "contentBlockDelta" in event:
        print(event["contentBlockDelta"]["delta"]["text"], end="", flush=True)
```

---

## 8. Tool Use (Function Calling)

### ⭐ Tool Use Flow (4 Steps)
```
1. Initial Request  → You send Claude a question + tool schemas
2. Tool Request     → Claude responds with a ToolUse part (tool name + args)
3. Tool Execution   → Your server runs the function, gets real data
4. Final Response   → You send tool result back; Claude gives complete answer
```

### ⭐ Why Tools Matter
- Claude's knowledge has a training cutoff — it cannot access real-time data
- Tools bridge Claude to: weather APIs, databases, reminder systems, file systems, etc.
- Claude can **request multiple tools in a single response** (parallel tool use)

### JSON Schema for Tools (Example)
```python
get_current_datetime_schema = {
    "toolSpec": {
        "name": "get_current_datetime",
        "description": "Returns the current date and time in the specified format. Use this tool when the user asks about the current time or date.",
        "inputSchema": {
            "json": {
                "type": "object",
                "properties": {
                    "date_format": {
                        "type": "string",
                        "description": "Python strftime format string. Default: '%Y-%m-%d %H:%M:%S'"
                    }
                },
                "required": []
            }
        }
    }
}
```

### JSON Schema Best Practices
- Write **3–4 sentence descriptions** for each tool (what it does, when to use it, what it returns)
- Provide **detailed descriptions** for each property
- Use **well-named, descriptive argument names** (they inform Claude's behavior)
- Include **validation** in your functions; raise errors if inputs fail
- **Return meaningful errors** — Claude will retry with adjusted input on error

### Tool Choice Configuration
```python
toolConfig={
    "tools": [schema1, schema2],
    "toolChoice": {"auto": {}}   # Claude decides
    # or: {"any": {}}             # Claude must use a tool
    # or: {"tool": {"name": "get_weather"}}  # Force specific tool
}
```

### ⭐ Handling Tool Use Response
```python
def chat(messages, tools=None, **kwargs):
    response = client.converse(**params)
    parts = response["output"]["message"]["content"]
    return {
        "parts": parts,
        "stop_reason": response["stopReason"],
        "text": "\n".join([p["text"] for p in parts if "text" in p])
    }

def run_tools(parts):
    tool_requests = [p for p in parts if "toolUse" in p]
    tool_result_parts = []
    for tr in tool_requests:
        tool_use_id = tr["toolUse"]["toolUseId"]
        tool_name   = tr["toolUse"]["name"]
        tool_input  = tr["toolUse"]["input"]
        try:
            output = run_tool(tool_name, tool_input)
            result = {"toolResult": {
                "toolUseId": tool_use_id,
                "content": [{"text": json.dumps(output)}],
                "status": "success"
            }}
        except Exception as e:
            result = {"toolResult": {
                "toolUseId": tool_use_id,
                "content": [{"text": f"Error: {e}"}],
                "status": "error"
            }}
        tool_result_parts.append(result)
    return tool_result_parts
```

### ⭐ Conversation Loop with Tool Use
```python
def run_conversation(messages):
    while True:
        result = chat(messages, tools=[schema1, schema2, schema3])
        add_assistant_message(messages, result["parts"])
        print(result["text"])

        if result["stop_reason"] != "tool_use":
            break

        tool_result_parts = run_tools(result["parts"])
        add_user_message(messages, tool_result_parts)

    return messages
```

### ⭐ Batch Tool — Parallel Tool Execution
Claude may not automatically parallelize independent tool calls. A **Batch Tool** forces parallel execution:
```json
{
  "name": "batch_tool",
  "description": "Invoke multiple other tool calls simultaneously",
  "input_schema": {
    "type": "object",
    "properties": {
      "invocations": {
        "type": "array",
        "items": {
          "properties": {
            "name": {"type": "string"},
            "arguments": {"type": "string", "description": "JSON string of arguments"}
          }
        }
      }
    }
  }
}
```

### Text Editor Tool (Built-in)
Claude has a built-in tool schema for file system operations. You only implement the handler:
- `view` — Read file or directory contents
- `str_replace` — Replace text in a file
- `create` — Create a new file
- `insert` — Insert text at a specific line
- `undo_edit` — Undo recent edits

```python
# Activate by version:
text_editor = "text_editor_20250124"  # Claude 3.7
text_editor = "text_editor_20241022"  # Claude 3.5
```

---

## 9. Retrieval-Augmented Generation (RAG)

### ⭐ Why RAG?
| Problem | Without RAG | With RAG |
|---------|-------------|---------|
| 800-page document | Can't fit in prompt | Only relevant chunks sent |
| Cost | High (full doc tokens) | Lower (selective chunks) |
| Performance | Degrades with long prompts | Stays consistent |
| Scale | Single file limit | Works across document collections |

### ⭐ Complete RAG Pipeline (7 Steps)
```
1. CHUNK      → Split document into manageable pieces
2. EMBED      → Generate vector embedding for each chunk
3. STORE      → Save embeddings + text in vector database
4. QUERY EMBED → Embed the user's question
5. SEARCH     → Find top-k most similar chunks (cosine similarity)
6. PROMPT     → Inject relevant chunks into prompt context
7. GENERATE   → Claude answers based on injected context
```

### Chunking Strategies
| Strategy | Method | Best For |
|----------|--------|---------|
| **Character-based** | Split by N characters with overlap | Quick implementation, general docs |
| **Sentence-based** | Split on `[.!?]` regex | Preserves sentence boundaries |
| **Section-based** | Split on `## ` (markdown headers) | Structured documents |

```python
def chunk_by_char(text, chunk_size=150, chunk_overlap=20):
    chunks, start_idx = [], 0
    while start_idx < len(text):
        end_idx = min(start_idx + chunk_size, len(text))
        chunks.append(text[start_idx:end_idx])
        start_idx = end_idx - chunk_overlap if end_idx < len(text) else len(text)
    return chunks

def chunk_by_section(document_text):
    return re.split(r"\n## ", document_text)
```

### ⭐ Cosine Similarity (How Vector Search Works)
```
cos(A, B) = (A · B) / (||A|| · ||B||)

Range: -1 to 1
  → 1   = identical / very similar
  → 0   = unrelated / perpendicular
  → -1  = opposite

Cosine Distance = 1 - Cosine Similarity
  (lower = more similar)
```

### ⭐ BM25 — Lexical Search
- **Problem with pure semantic search**: A specific ID like `INC-2023-Q4-011` may not be found by embeddings because semantics ≠ exact match.
- **BM25 algorithm**:
  1. Tokenize the query
  2. Count term frequency across documents
  3. Weight terms by **rarity** (rare terms → higher importance)
  4. Score chunks that contain more high-weight terms

### ⭐ Hybrid Search (Semantic + Lexical)
```
VectorIndex (semantic) + BM25Index (lexical)
        ↓
Reciprocal Rank Fusion (RRF):
    RRF_score(d) = Σ(1 / (k + rank_i(d)))

Final ranking merges both result lists by RRF score
```

### LLM-Based Re-ranking
- After hybrid search, pass top-N results to Claude
- Ask Claude to re-order by relevance to user's question
- Use **document IDs** (not full text) in Claude's response for efficiency
- Trade-off: Higher latency, significantly better relevance

### ⭐ Contextual Retrieval
Add situating context to each chunk **before** storing in the vector database:
```python
def add_context(text_chunk, source_text):
    prompt = f"""Write a short snippet to situate this chunk within the 
    overall source document for better search retrieval.
    
    <document>{source_text}</document>
    <chunk>{text_chunk}</chunk>
    
    Answer only with the succinct context."""
    
    result = chat(messages)
    return result["text"] + "\n" + text_chunk  # prepend context to chunk
```

---

## 10. Model Context Protocol (MCP)

### ⭐ What is MCP?
MCP is a **standard communication protocol** (created by Anthropic) that:
- Acts as a "USB port" for AI — any data source can plug in
- Provides Claude with **pre-built tool schemas and implementations** via MCP Servers
- Eliminates writing custom tool functions for every external service
- Enables structured access to: GitHub, AWS, databases, and more

### MCP vs. Direct Tool Use
| Aspect | Direct Tool Use | MCP |
|--------|----------------|-----|
| Tool schemas | You write them | MCP Server provides them |
| Tool functions | You implement them | MCP Server handles execution |
| Maintenance | You maintain | Server owner maintains |
| Setup effort | High (per integration) | Low (connect to server) |

### MCP Architecture
```
YOUR SERVER (MCP Client) ←→ MCP SERVER (GitHub / AWS / DB)
                                    │
                              Pre-built tools:
                              - list_repos()
                              - get_pull_requests()
                              - create_issue()
                              (all already implemented)
```

### MCP Implementation in This Project
The project uses MCP **concepts** (not a full MCP server) — implementing the same data-injection pattern:
```
Traditional:  User → Claude (guesses, no real data)
MCP Pattern:  User → App fetches REAL DB data → Injects as context → Claude analyzes REAL data

Example:
"Which jobs have the least competition?"
→ Query MySQL for jobs
→ Build structured text context from DB rows
→ Inject as system context into Claude prompt
→ Claude answers based on actual job data
```

---

## 11. Advanced Features

### ⭐ Extended Thinking
```python
additional_model_fields["thinking"] = {
    "type": "enabled",
    "budget_tokens": 1024  # minimum; increase for complex tasks
}
```
- Response now has **two content parts**:
  - **Reasoning Part**: Claude's internal step-by-step thinking
  - **Text Part**: Final response
- **Cryptographic signature** attached to reasoning content prevents tampering
- **Redacted content**: Sometimes reasoning is flagged by safety systems and encrypted (but still functional for multi-turn use)
- **Trade-offs**: Better accuracy ↔ Higher cost + Higher latency
- **When to use**: Only after optimizing prompts AND still needing better accuracy on complex tasks

### ⭐ Prompt Caching
```
PROBLEM: Every API call re-processes the same content (conversation history, large system prompts, tool schemas).

SOLUTION: Cache points tell Claude what to save.

Cache Lifespan: 5 minutes
Min Content: 1024 tokens before the cache point
Cost: Cached reads are cheaper than uncached processing

Most Common Cache Targets:
  → System prompts (rarely change)
  → Tool schemas (same across requests)
  → Large document contexts (in RAG pipelines)
```

```python
# Adding cache points to tool list
tools = [
    {"toolSpec": add_duration_to_datetime_schema},
    {"toolSpec": get_current_datetime_schema},
    {"cachePoint": {"type": "default"}}  # ← cache everything above this
]

# Adding cache point to system prompt
system = [
    {"text": "You are a senior software engineer..."},
    {"cachePoint": {"type": "default"}}
]
```

### Vision / Image Processing
- **Limits**: Up to 20 images per request, max 3.75MB, max 8000px dimensions
- **Token cost**: `tokens = (width × height) / 750`
- **Format**: Sent as `{"image": {"format": "png", "source": {"bytes": image_bytes}}}`
- **Key insight**: Same prompt engineering techniques (CoT, few-shot) dramatically improve vision accuracy

### PDF Processing
```python
add_user_message(messages, [
    {"document": {
        "format": "pdf",
        "name": "earth",              # filename without extension
        "source": {"bytes": file_bytes},
        "citations": {"enabled": True}  # optional: enables source attribution
    }},
    {"text": "Summarize this document"}
])
```

### Citations
- When `citations.enabled = True`, Claude returns both text parts AND citation parts
- Citation parts map statements back to specific document pages and text
- Provides verification, confidence, and transparency for PDF-based applications

---

## 12. Project Architecture: Spring Boot + Bedrock

### System Architecture
```
Browser/Postman
      │
      ▼
JS Client Microservice (Express, Port 3000)   ← Proxy / BFF layer
      │
      ▼
Spring Boot API (Tomcat, Port 8080)
      ├── Controller Layer
      ├── Bucket4j Rate Limiter (10 req/min token bucket)
      ├── JobService → MySQL Database (linkedin_naukr_jobs)
      └── BedrockService → AWS Bedrock (Claude 3.5 Sonnet)
```

### Project Folder Structure (Key Files)
```
AWS_BedrockProject_withClaude/
├── bedrock-api/                      # Spring Boot Microservice
│   └── src/main/java/com/awsbedrock/api/
│       ├── config/
│       │   ├── AwsBedrockConfig.java         # BedrockRuntimeClient bean
│       │   ├── RateLimitConfig.java          # Bucket4j token bucket
│       │   └── WebConfig.java               # CORS configuration
│       ├── controller/
│       │   ├── BedrockController.java        # /chat, /models endpoints
│       │   └── JobAnalysisController.java    # /jobs/analyze (MCP)
│       ├── service/
│       │   ├── BedrockServiceImpl.java       # Converse API call logic
│       │   └── JobService.java              # DB query + context builder
│       └── exception/
│           └── GlobalExceptionHandler.java   # Centralized error handling
│
├── bedrock-js-client/                # Express.js Proxy Microservice
└── docs/
    ├── README.md                     # Full architecture documentation
    └── API_WORKFLOW.md              # Endpoint reference & Mermaid diagram
```

### ⭐ MCP Flow (Job Analysis)
```
POST /api/v1/jobs/analyze  {"question": "Which Java jobs have least competition?", "keyword": "Java"}
        │
        ├── 3a. Rate limit check (Bucket4j)
        ├── 3b. JobService.searchJobs("Java") → SQL: SELECT * FROM jobs WHERE title ILIKE '%Java%'
        ├── 3c. buildContextForBedrock(jobs) → Converts DB rows to structured text
        ├── 3d. Build combined prompt: system + database context + user question
        └── 3e. BedrockService.chat(combinedPrompt) → AWS SDK Converse API call
```

### REST API Endpoints (Quick Reference)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/chat` | Direct prompt to Bedrock model |
| POST | `/api/v1/jobs/analyze` | MCP job analysis with DB context |
| GET | `/api/v1/jobs` | List all job listings |
| GET | `/api/v1/jobs/{id}` | Get specific job |
| GET | `/api/v1/jobs/search?keyword=Java` | Search jobs by keyword |
| GET | `/api/v1/jobs/stats` | Job statistics |
| GET | `/api/v1/models` | List available Bedrock models |
| GET | `/api/v1/health` | Health check |

---

## 13. Security, IAM & Deployment

### ⭐ IAM — Minimum Required Policy
```json
{
  "Statement": [{
    "Effect": "Allow",
    "Action": [
      "bedrock:InvokeModel",
      "bedrock:InvokeModelWithResponseStream"
    ],
    "Resource": [
      "arn:aws:bedrock:us-east-1::foundation-model/anthropic.claude-3-5-sonnet-*"
    ]
  }]
}
```
> ⚠️ **NEVER use `"bedrock:*"` + `"Resource": "*"` in production.** Always apply Least Privilege Principle.

### ⭐ AWS Credential Provider Chain (Order)
```
DefaultCredentialsProvider checks in this order:
  1. Java System Properties
  2. Environment Variables          ← Development
  3. AWS credentials file (~/.aws)  ← Development
  4. AWS config file (SSO)          ← SSO users
  5. ECS Container Credentials     ← ECS/Fargate
  6. EC2 Instance Profile           ← EC2 production
```

### Credential Methods by Environment
| Environment | Method | Security Level |
|-------------|--------|----------------|
| Development | Environment variables or `~/.aws/credentials` | Medium |
| EC2 Production | IAM Instance Profile (role attached to EC2) | High ✅ |
| ECS/Fargate | ECS Task Role | Highest ✅ |
| Lambda | Lambda Execution Role | Highest ✅ |

### Security Best Practices
```java
// ❌ NEVER hard-code credentials
StaticCredentialsProvider.create(AwsBasicCredentials.create("KEY", "SECRET"))

// ✅ ALWAYS use DefaultCredentialsProvider
BedrockRuntimeClient.builder().credentialsProvider(DefaultCredentialsProvider.create())
```

- **Input validation**: `@NotBlank`, `@Size(max=100000)` on prompt fields
- **Error handling**: `GlobalExceptionHandler` returns generic messages (no stack traces to client)
- **HTTPS**: Enable SSL in production `application-prod.yml`
- **CORS**: Restrict to specific origins in production (`WebConfig.java`)
- **DB credentials**: Use environment variables (`${DB_PASSWORD}`), never plain YAML

### Deployment Options Comparison
| Option | Best For | Pros | Cons |
|--------|---------|------|------|
| **EC2** | Full control, predictable workloads | Easy to debug, IAM Instance Profile | Manual scaling, pay when idle |
| **ECS Fargate** ⭐ | Container workloads, production | Auto-scaling, Task Role (most secure), managed | More complex setup |
| **Lambda** | Sporadic traffic, cost optimization | Pay per invocation, auto-scales | Cold starts (5–15s Java), 15min timeout limit |

### Rate Limiting (Bucket4j)
```
Algorithm:  Token Bucket
Capacity:   10 tokens
Refill:     10 tokens per minute (greedy)

Request 1–10:  ✅ Allowed
Request 11:   ❌ HTTP 429 Too Many Requests
After 6s:     ✅ 1 token refilled
```

### Docker & Kubernetes Commands
```bash
# Build and run with Docker
docker build -t bedrock-api:1.0.0 .
docker run -d -p 8080:8080 \
  -e AWS_ACCESS_KEY_ID=... \
  -e AWS_REGION=us-east-1 \
  bedrock-api:1.0.0

# Kubernetes deployment
kubectl create secret generic aws-credentials --from-literal=access-key-id=KEY
kubectl apply -f bedrock-api/k8s/deployment.yaml
kubectl get pods -l app=bedrock-api
```

---

## 14. Prompt Evaluation & Testing Framework

### ⭐ Why Evaluate Prompts?
Prompt evaluation answers: "Which version of my prompt produces better outputs?" It provides an **objective, data-driven** approach to prompt improvement instead of guessing.

### ⭐ Evaluation Pipeline Architecture
```
PHASE 1 — Dataset Generation:
  task_description + prompt_inputs_spec
        │
        ▼
  Claude generates unique ideas → test cases
        │
        ▼
  dataset.json  [{"prompt_inputs": {...}, "solution_criteria": [...]}]

PHASE 2 — Prompt Execution:
  For each test case → run_prompt(inputs) → model_output

PHASE 3 — Grading:
  (test_case + output + solution_criteria)
        │
        ▼
  Claude grades output → JSON {strengths, weaknesses, reasoning, score: 1–10}

PHASE 4 — Reporting:
  Average score, JSON output, HTML report
```

### Scoring Rubric
| Score | Meaning |
|-------|---------|
| 1–3 | Fails one or more **mandatory** requirements |
| 4–6 | Meets mandatory requirements but has significant deficiencies |
| 7–8 | Meets all mandatory + most secondary criteria; minor issues |
| 9–10 | Meets all mandatory + secondary criteria fully |

### Key PromptEvaluator Methods
```python
evaluator = PromptEvaluator(max_concurrent_tasks=2)

# Generate dataset
dataset = evaluator.generate_dataset(
    task_description="Extract topics from scholarly text into JSON array",
    prompt_inputs_spec={"content": "One paragraph of scholarly English text"},
    output_file="dataset.json",
    num_cases=4
)

# Run evaluation
results = evaluator.run_evaluation(
    run_prompt_function=run_prompt,
    dataset_file="dataset.json",
    extra_criteria="...",          # mandatory requirements (auto-fail on violation)
    json_output_file="output.json",
    html_output_file="output.html"
)
```

### Important Implementation Details
- Uses `concurrent.futures.ThreadPoolExecutor` for parallel test case generation and grading
- Dataset generation uses `temperature=0.7` for creative diversity
- Grading uses `temperature=0.0` for consistent, deterministic scoring
- `stopSequences=["```"]` used to force JSON-only output from model
- **Prefill technique**: `add_assistant_message(messages, "```json")` forces model to start with JSON code block

---

## 15. Certification-Relevant Topics ⭐

> These topics appear most frequently in AWS AI/ML certifications and Anthropic certification assessments.

### ⭐⭐⭐ Critical Topics (Must Know)
1. **Bedrock is serverless** — no infrastructure management, pay-per-token
2. **Converse API** is the recommended unified API for all models
3. **Inference Profiles** solve cross-region model availability
4. **Bedrock stores no data** — your prompts never leave AWS, no training on your data
5. **IAM authentication** via SigV4 signature (not API keys like OpenAI)
6. **No conversation state** in Bedrock — you must maintain message history
7. **Tool use flow**: 4 steps (Initial → Tool Request → Tool Execution → Final Response)
8. **stopReason "tool_use"** signals Claude wants to call a function
9. **RAG** solves long-document limitations via chunk → embed → retrieve → prompt
10. **MCP** provides pre-built tool implementations; reduces integration code

### ⭐⭐ Important Topics
- Temperature parameter behavior (0.0 deterministic, 1.0 random)
- Prompt caching: 5-minute cache, 1024 token minimum, cache points must be explicitly set
- Extended thinking: reasoning tokens are paid, min 1024 budget
- Cosine similarity for vector search (higher = more similar)
- BM25 for exact keyword matching in hybrid search
- RRF formula for merging ranked result lists
- Model selection by use case (Haiku for speed/cost, Sonnet for quality)
- Credential provider chain order
- Least privilege IAM policies for Bedrock

### ⭐ Supporting Topics
- Chunk overlap strategy to avoid losing context at boundaries
- Contextual retrieval — adds document-level context to each chunk
- LLM re-ranking — Claude reorders hybrid search results
- Batch tool pattern for forcing parallel tool calls
- Text Editor Tool — only schema is built-in; implementation is your responsibility
- PDF processing — `"document"` content type with optional `citations.enabled`
- Vision limits: 20 images max, 3.75MB, `tokens = (w × h) / 750`

---

## 16. Code Examples & Patterns

### Pattern 1: Basic Bedrock Chat (Python)
```python
import boto3, json

client = boto3.client("bedrock-runtime", region_name="us-west-2")

def chat(messages, system=None, temperature=1.0, stop_sequences=[], tools=None):
    params = {
        "modelId": "us.anthropic.claude-3-5-sonnet-20241022-v2:0",
        "messages": messages,
        "inferenceConfig": {"temperature": temperature, "stopSequences": stop_sequences}
    }
    if system:
        params["system"] = [{"text": system}]
    if tools:
        params["toolConfig"] = {"tools": tools}

    response = client.converse(**params)
    parts = response["output"]["message"]["content"]
    return {
        "parts": parts,
        "stop_reason": response["stopReason"],
        "text": "\n".join([p["text"] for p in parts if "text" in p])
    }
```

### Pattern 2: Multi-Turn Conversation with Tools
```python
def run_conversation(user_input, tools):
    messages = []
    add_user_message(messages, user_input)

    while True:
        result = chat(messages, tools=tools)
        add_assistant_message(messages, result["parts"])

        if result["stop_reason"] != "tool_use":
            break

        tool_results = run_tools(result["parts"])
        add_user_message(messages, tool_results)

    return result["text"]
```

### Pattern 3: RAG Retrieval + Prompt
```python
def answer_with_rag(user_query, retriever):
    query_embedding = generate_embedding(user_query)
    results = retriever.search(query_embedding, k=3)

    context = "\n\n".join([doc["content"] for doc, _ in results])

    messages = []
    add_user_message(messages, f"""Answer based on the context below:

<context>
{context}
</context>

<question>{user_query}</question>""")

    return chat(messages)["text"]
```

### Pattern 4: Prefill (Forcing Output Format)
```python
# Force Claude to start in a JSON code block
messages = []
add_user_message(messages, "Generate a JSON object with name and age fields.")
add_assistant_message(messages, "```json")  # Prefill — Claude MUST continue from here

result = chat(messages, stop_sequences=["```"])
parsed = json.loads(result["text"])
```

---

## 17. Interview Q&A

**Q1: What is AWS Bedrock and how does it differ from calling OpenAI's API?**
> AWS Bedrock is a **fully managed service** offering access to multiple FM providers (Anthropic, Meta, Amazon, Mistral) through a single API. Key differences: authentication uses **AWS IAM/SigV4** (not API keys), data stays **within your AWS region** and is never used for training, supports multiple model providers (not just one), and compliance includes HIPAA, FedRAMP, SOC 2.

**Q2: Why must you pass the full conversation history with every Bedrock API request?**
> Bedrock is **stateless** — it does not store any conversation state between requests. Every call is treated as independent. You must maintain and pass the complete `messages` list with every request to preserve conversation context.

**Q3: What is an Inference Profile and when would you use it?**
> An Inference Profile is a Bedrock construct that abstracts regional model availability. It **automatically routes your request to a region where the requested model is hosted**, even if you're connecting from a different region. You use it when a model isn't available in your primary region or when you want fault-tolerant cross-region routing.

**Q4: Explain the tool use flow in Claude with 4 steps.**
> 1. **Initial Request**: Send Claude a user question + tool schemas (JSON describing available functions). 2. **Tool Request**: Claude responds with a `ToolUse` content part containing the tool name, `toolUseId`, and input arguments. 3. **Tool Execution**: Your server runs the actual function and gets real-world data. 4. **Final Response**: Send back the tool results as a `ToolResult` message; Claude incorporates them and gives the final answer.

**Q5: What is the difference between semantic search and BM25 in a RAG pipeline, and why use both?**
> **Semantic search** uses vector embeddings to find conceptually related content — great for understanding meaning but can miss exact term matches. **BM25** is a lexical algorithm that weights terms by rarity and finds exact keyword matches — perfect for specific IDs or names but misses semantic context. **Hybrid search** combines both with Reciprocal Rank Fusion (RRF) to get the best of both: conceptual relevance AND exact match precision.

**Q6: What is MCP and how does it differ from regular tool use?**
> MCP (Model Context Protocol) is a **standard protocol** that shifts the burden of tool implementation from you to dedicated MCP Servers. With regular tool use, you write every schema and function yourself. With MCP, you connect to a pre-built server (e.g., GitHub MCP Server) that already has all tool schemas and implementations defined. MCP reduces integration code dramatically.

**Q7: What is prompt caching and when is it effective?**
> Prompt caching stores Claude's preprocessing work (tokenization, embeddings) for **5 minutes**. On subsequent requests containing the same content before a cache point, Claude reads from cache instead of reprocessing. It's effective when: the same system prompt, tool schemas, or conversation history is sent repeatedly within a session. Minimum: **1024 tokens** before the cache point.

**Q8: How do you implement a stateful multi-turn chatbot with tool use?**
> Maintain a `messages` list. Use a **conversation loop**: (1) send messages + tools to Claude, (2) add Claude's response to `messages`, (3) check `stop_reason` — if `"tool_use"`, execute tools and add results as a user message, then loop; if `"end_turn"`, exit the loop and return the final text to the user.

**Q9: What is Extended Thinking and when should you use it?**
> Extended Thinking gives Claude a budget of "reasoning tokens" to think through complex problems before generating a final response. The response includes a reasoning content part (Claude's internal monologue) and the final text part. Use it **only after you've optimized your prompt and still need better accuracy on complex tasks** — it increases both cost and latency.

**Q10: What are the key security best practices when using AWS Bedrock in production?**
> (1) Never hard-code credentials — use `DefaultCredentialsProvider`. (2) Use IAM roles (Instance Profile for EC2, Task Role for ECS) instead of static keys. (3) Apply least-privilege IAM policies (restrict to specific model ARNs). (4) Validate all user inputs before sending to Bedrock. (5) Return generic error messages to clients (log details server-side). (6) Enable HTTPS/TLS in production. (7) Store DB passwords as environment variables, not in YAML files. (8) Use rate limiting to prevent cost spikes.

---

## 18. Certification Practice Q&A

**Q1: A developer receives a "model does not exist" error when calling a Claude model via Bedrock. What is the MOST likely cause?**
> A) The model is not enabled in the Bedrock console  
> B) The model is not available in the specified AWS region  
> C) The IAM policy is missing `bedrock:ListFoundationModels`  
> D) The API key is invalid  
> ✅ **Answer: B** — Not all models are available in all regions. **Solution**: Use an Inference Profile for cross-region routing, OR ensure you're in a region where the model is available.

**Q2: Which Bedrock API should you use for building a production chatbot that needs to work across multiple model providers?**
> A) InvokeModel  
> B) ListFoundationModels  
> C) Converse API  
> D) ConverseStream  
> ✅ **Answer: C** — The Converse API is the recommended, model-agnostic unified API. InvokeModel requires model-specific JSON payloads.

**Q3: In a RAG pipeline, a user queries "What happened with INC-2023-Q4-011?" Semantic search returns unrelated financial sections. What should you add?**
> A) Increase embedding dimensions  
> B) Add BM25 lexical search with hybrid fusion  
> C) Increase chunk size  
> D) Use Extended Thinking  
> ✅ **Answer: B** — Specific identifiers require exact keyword matching. BM25 with RRF-based hybrid fusion finds exact term matches that semantic search misses.

**Q4: Claude returns a response with `stopReason = "tool_use"`. What must your application do next?**
> A) Display the text response to the user  
> B) Retry the same request with higher temperature  
> C) Extract tool use parts, execute the functions, and send results back to Claude  
> D) Increase the `maxTokens` parameter  
> ✅ **Answer: C** — `tool_use` stop reason means Claude wants to call external functions. You must extract the `ToolUse` parts, run the actual functions, and send back `ToolResult` parts.

**Q5: Which IAM credential method is MOST secure for a production Spring Boot app running on Amazon ECS?**
> A) Hard-coded AWS access keys in application.yml  
> B) Environment variables passed to the container  
> C) ECS Task Role (IAM role assigned to the task definition)  
> D) AWS credentials file mounted as a volume  
> ✅ **Answer: C** — ECS Task Role is automatically assumed by the container. No credential management code needed, and credentials rotate automatically.

**Q6: What is the minimum token count required before a cache point for prompt caching to be effective?**
> A) 256 tokens  
> B) 512 tokens  
> C) 1024 tokens  
> D) 4096 tokens  
> ✅ **Answer: C** — Prompt caching requires a minimum of **1024 tokens** of content before the cache point to qualify for caching.

**Q7: You want Claude to make two independent date calculations simultaneously instead of sequentially. What is the BEST approach?**
> A) Use two separate API calls in parallel threads  
> B) Implement a Batch Tool that Claude uses to invoke multiple sub-tools at once  
> C) Set toolChoice to "any"  
> D) Increase temperature to 1.0  
> ✅ **Answer: B** — The Batch Tool pattern forces Claude to package multiple independent tool calls into a single `batch_tool` invocation, executing them in parallel.

**Q8: What temperature value should you use when grading model outputs in a prompt evaluation pipeline?**
> A) 1.0 (maximum diversity)  
> B) 0.7 (creative balance)  
> C) 0.0 (deterministic/consistent)  
> D) 0.5 (moderate)  
> ✅ **Answer: C** — Grading/evaluation must be deterministic and consistent. `temperature=0.0` ensures the same scoring criteria produce the same scores across runs.

---

## 19. Revision Quick Notes

### 5-Minute Revision Checklist

**AWS Bedrock Basics:**
- [ ] Fully managed, serverless, pay-per-token
- [ ] Models hosted in AWS (not provider's infrastructure)
- [ ] Data never leaves AWS, never used for training
- [ ] Authentication: IAM/SigV4 (not API keys)
- [ ] Converse API = unified API for all models
- [ ] Inference Profiles = cross-region routing

**Message Management:**
- [ ] Bedrock is STATELESS — you maintain message history
- [ ] Messages alternate: user → assistant → user → assistant
- [ ] Content is a list of parts (text, image, document, toolUse, toolResult)

**Tool Use:**
- [ ] 4-step flow: Request → Tool Request → Execution → Final
- [ ] JSON Schema describes each tool to Claude
- [ ] `stopReason = "tool_use"` → run tools, add results, continue loop
- [ ] `toolUseId` must match in tool results
- [ ] Batch Tool forces parallel execution

**RAG Pipeline:**
- [ ] 7 steps: Chunk → Embed → Store → Query Embed → Search → Prompt → Generate
- [ ] Semantic (vector) + Lexical (BM25) = Hybrid Search
- [ ] RRF merges ranked lists: `Σ(1/(k + rank_i(d)))`
- [ ] LLM re-ranker improves relevance (costs latency)
- [ ] Contextual retrieval adds document context to chunks before storage

**Advanced:**
- [ ] Prompt caching: 5 min lifetime, 1024 token min, explicit cache points
- [ ] Extended thinking: reasoning tokens, signature, min 1024 budget
- [ ] PDF: `"document"` content type, optional `citations.enabled`
- [ ] Vision: 20 img max, 3.75MB, tokens = (w×h)/750

**Security:**
- [ ] DefaultCredentialsProvider always; never hard-code keys
- [ ] EC2 = Instance Profile; ECS = Task Role
- [ ] Least privilege IAM; restrict to specific model ARNs
- [ ] Rate limiting (Bucket4j): token bucket, 10 req/min

---

## 20. One-Page Summary

### Claude with Amazon Bedrock — Essential Reference

**WHAT IS BEDROCK?** Fully managed AWS service for foundation model inference. Pay-per-token, serverless, no data retention, multi-provider (Anthropic/Meta/Amazon/Mistral).

**HOW TO CALL:** Use `boto3.client("bedrock-runtime")` + `client.converse()`. Always prefer **Converse API** (model-agnostic) over InvokeModel. Use **Inference Profiles** for cross-region reliability.

**KEY PARAMETERS:** `temperature` (0.0=deterministic, 1.0=random), `maxTokens`, `topP`, `stopSequences`. Check `stopReason`: `end_turn`, `max_tokens`, `tool_use`, `stop_sequence`.

**CONVERSATION STATE:** Bedrock is stateless. Maintain `messages` list manually. Alternate user/assistant roles. Pass full history every request.

**TOOL USE (4 STEPS):** (1) Send tools schemas + question. (2) Claude returns `ToolUse` part. (3) You execute the function. (4) Send `ToolResult` back; Claude answers. Loop on `stopReason="tool_use"`.

**RAG (7 STEPS):** Chunk → Embed → Store → QueryEmbed → Search (Semantic+BM25) → Prompt → Generate. Hybrid search uses **RRF** to merge rankings. Contextual retrieval adds document context to chunks pre-storage.

**MCP:** Protocol for pre-built tool integrations. Connect to MCP Server → get GitHub/AWS/DB tools without writing code. Same end result as tool use, less dev effort.

**ADVANCED:** Prompt Caching (5min, 1024 token min, explicit cache points). Extended Thinking (reasoning tokens, higher cost/latency, last resort). Vision (20 img, 3.75MB). PDFs (document content type + optional citations).

**SECURITY:** Never hard-code credentials. Use DefaultCredentialsProvider. EC2=Instance Profile, ECS=Task Role. Least privilege IAM. Rate limiting. Input validation. HTTPS in prod.

**EVALUATION:** Dataset (Claude generates test cases) → Execute prompt → Grade output (Claude scores 1–10) → Report. Use `temperature=0.0` for grading.

---

## 21. Top Key Takeaways

1. 🏗️ **AWS Bedrock = Zero Infrastructure** — You only write API calls. AWS handles model hosting, scaling, and security at the infrastructure level.

2. 🔑 **Converse API is King** — One API, one code structure, works with ALL models. Never use InvokeModel unless absolutely necessary.

3. 📜 **Bedrock is Stateless** — You own conversation state. Pass the complete message history with every request. This is the #1 source of bugs for beginners.

4. 🔄 **Tool Use is a Loop** — It's not one request/response. It's a cycle: request → tool request → execution → final response. Always check `stopReason`.

5. 🔍 **RAG = Chunk + Embed + Retrieve** — The power of RAG is selective injection. Don't dump entire documents — find and inject only the relevant chunks.

6. 🔀 **Hybrid Search > Semantic Only** — Combine vector search (semantic meaning) + BM25 (exact keywords) + RRF (merge rankings) for superior RAG retrieval.

7. 🌐 **MCP = Pre-built Tool Ecosystem** — Instead of writing GitHub/AWS integrations from scratch, MCP servers provide ready-to-use tool implementations.

8. ⚡ **Prompt Caching = Cost Optimization** — For apps with repeated system prompts or conversation history, caching can significantly reduce token costs.

9. 🧠 **Extended Thinking = Last Resort** — Only enable after prompt optimization fails. It's expensive and slow but dramatically improves complex task accuracy.

10. 🛡️ **Security is Non-Negotiable** — DefaultCredentialsProvider always. IAM roles over static keys. Least privilege IAM policies. Validate all inputs before sending to Bedrock.

11. 📊 **Evaluate, Don't Guess** — Use the PromptEvaluator pipeline to objectively compare prompts with measurable scores (1–10) instead of intuition.

12. 💡 **MCP in Practice = Context Injection** — Even without a full MCP server, the MCP pattern (fetch real DB data → inject as context → Claude analyzes) dramatically improves answer quality over pure LLM guessing.

---

*Generated for: Claude Architect Certification Preparation*
*Source: Claude with Amazon Bedrock learning materials + AWS BedrockProject_withClaude source code & documentation*
*Covers: AWS Bedrock API · Claude Models · Tool Use · RAG · MCP · Prompt Evaluation · Security · Deployment*

---

## 22. 🔬 Real Project Code Walkthrough — `bedrock-api` (Spring Boot)

> These are **actual production-ready code snippets** from your own project at `AWS_BedrockProject_withClaude/bedrock-api`. Each snippet is explained line-by-line for deep understanding.

---

### 22.1 Application Entry Point — `BedrockApiApplication.java`

**Purpose:** Bootstraps the entire Spring Boot application — creates the IoC container, starts Tomcat, and scans for all beans.

```java
@SpringBootApplication          // Combines 3 annotations:
                                //  → @Configuration    (this class defines beans)
                                //  → @EnableAutoConfiguration (Spring auto-wires dependencies)
                                //  → @ComponentScan    (scans all sub-packages for @Service, @Controller, etc.)
public class BedrockApiApplication {

    public static void main(String[] args) {
        // SpringApplication.run() does:
        //  1. Creates the Spring ApplicationContext (IoC container)
        //  2. Starts embedded Tomcat (default port 8080)
        //  3. Registers all beans found via component scanning
        //  4. Applies auto-configuration (Jackson, Actuator, HikariCP, etc.)
        SpringApplication.run(BedrockApiApplication.class, args);
    }
}
```

**📌 Interview Points:**
- `@SpringBootApplication` is a meta-annotation (3-in-1)
- Spring Boot auto-configures Tomcat because `spring-boot-starter-web` is on the classpath
- `SpringApplication.run()` returns an `ApplicationContext` — the bean registry

---

### 22.2 AWS Bedrock Client Configuration — `AwsBedrockConfig.java`

**Purpose:** Creates and configures the `BedrockRuntimeClient` singleton that the entire app uses to call Bedrock.

```java
@Configuration              // Marks this class as a Spring bean factory
public class AwsBedrockConfig {

    @Value("${aws.bedrock.region:us-east-1}")   // Reads from application.yml
    private String awsRegion;                    // Fallback: us-east-1

    @Value("${aws.bedrock.access-key-id:}")      // Optional: empty string if not set
    private String accessKeyId;

    @Value("${aws.bedrock.secret-access-key:}")
    private String secretAccessKey;

    @Bean                                        // Spring calls this method to create a singleton bean
    public BedrockRuntimeClient bedrockRuntimeClient() {

        // ⭐ CREDENTIAL RESOLUTION LOGIC
        // If access-key-id is explicitly set → use StaticCredentialsProvider (dev only)
        // Otherwise → use DefaultCredentialsProvider (checks env vars, ~/.aws, EC2 role, ECS role)
        AwsCredentialsProvider credentialsProvider;
        if (accessKeyId != null && !accessKeyId.trim().isEmpty() &&
            secretAccessKey != null && !secretAccessKey.trim().isEmpty()) {
            // ⚠️  Static credentials — only for local development
            credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
            );
        } else {
            // ✅ Recommended for production — auto-picks the correct credential source
            credentialsProvider = DefaultCredentialsProvider.create();
        }

        return BedrockRuntimeClient.builder()
                .region(Region.of(awsRegion))               // e.g., Region.US_EAST_1
                .credentialsProvider(credentialsProvider)
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .apiCallTimeout(Duration.ofSeconds(120))        // Total call time incl. retries
                        .apiCallAttemptTimeout(Duration.ofSeconds(60))  // Single attempt timeout
                        .retryPolicy(RetryPolicy.builder()
                                .numRetries(3)              // Retries on throttle/network errors
                                .build())                   // Uses exponential backoff automatically
                        .build())
                .build();
    }
}
```

**📌 Key Concepts:**
| Concept | Explanation |
|---------|-------------|
| `@Configuration` | Tells Spring this class provides `@Bean` method definitions |
| `@Value("${key:default}")` | Injects from `application.yml`. Colon `:` provides a fallback |
| `@Bean` | Spring calls this method once at startup and stores the result in the IoC container |
| `DefaultCredentialsProvider` | The credential chain — checks env vars → `~/.aws` → ECS → EC2. Never hard-code! |
| `apiCallTimeout` | Max total time for the API call including all retries (set high for Bedrock — models can take 60s+) |

---

### 22.3 Request & Response DTOs

**Purpose:** Define the contract between the HTTP client and the API. DTOs also validate input before it reaches business logic.

#### `PromptRequest.java` — Inbound DTO
```java
@Data               // Generates: getters, setters, toString, equals, hashCode
@Builder            // Enables: PromptRequest.builder().prompt("...").build()
@NoArgsConstructor  // Needed by Jackson to deserialize JSON
@AllArgsConstructor // Needed by @Builder internals
public class PromptRequest {

    private String modelId;         // Optional — uses default if null

    @NotBlank(message = "Prompt is required and cannot be blank")
    @Size(min = 1, max = 100000, message = "Prompt must be between 1 and 100,000 characters")
    private String prompt;          // ← REQUIRED field

    @Size(max = 10000)
    private String systemPrompt;    // Optional persona/instruction for the model

    @Min(1) @Max(8192)
    @Builder.Default
    private Integer maxTokens = 4096;   // Default 4096 ≈ 3000 words

    // Temperature guide:
    //  0.0 → Code generation, factual Q&A (deterministic)
    //  0.5 → Chat, summaries (balanced)
    //  0.9 → Creative writing, brainstorming (creative)
    @Min(0) @Max(1)
    @Builder.Default
    private Double temperature = 0.7;

    @Min(0) @Max(1)
    @Builder.Default
    private Double topP = 0.9;      // Nucleus sampling threshold
}
```

#### `BedrockResponse.java` — Outbound DTO
```java
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // ← Omits null fields from JSON response
public class BedrockResponse {

    private String  response;       // The AI-generated text
    private String  modelId;        // Which model was used (useful if default was applied)
    private Integer inputTokens;    // Charged tokens — user prompt + system prompt
    private Integer outputTokens;   // Charged tokens — model's response (more expensive!)
    private Integer totalTokens;    // inputTokens + outputTokens
    private Long    latencyMs;      // How long the call took (for SLA monitoring)

    // stopReason values:
    //  "end_turn"         → Model finished naturally ✅
    //  "max_tokens"       → Response truncated ⚠️ (increase maxTokens)
    //  "stop_sequence"    → Hit a custom stop sequence
    //  "content_filtered" → Response blocked by content moderation
    private String stopReason;
}
```

**📌 Interview Points:**
- `@JsonInclude(NON_NULL)` prevents null fields from cluttering the JSON response
- `@NotBlank` fails fast before calling Bedrock (prevents wasted API calls on empty prompts)
- `@Builder.Default` is required when using `@Builder` + field-level defaults together

---

### 22.4 Core Business Logic — `BedrockServiceImpl.java`

**Purpose:** The heart of the application — builds the Converse API request, calls Bedrock, parses and returns the response.

```java
@Slf4j                          // Generates: private static final Logger log = LoggerFactory.getLogger(...)
@Service                        // Registers as a Spring service bean (business logic layer)
@RequiredArgsConstructor        // Lombok: generates constructor for all `final` fields (= constructor injection)
public class BedrockServiceImpl implements BedrockService {

    private final BedrockRuntimeClient bedrockRuntimeClient;  // Injected from AwsBedrockConfig

    @Value("${aws.bedrock.default-model:apac.anthropic.claude-3-sonnet-20240229-v1:0}")
    private String defaultModelId;

    @Value("${aws.bedrock.max-tokens:4096}")
    private int defaultMaxTokens;

    @Value("${aws.bedrock.temperature:0.7}")
    private double defaultTemperature;

    @Override
    public BedrockResponse chat(PromptRequest request) {
        long startTime = System.currentTimeMillis();

        // STEP 1: Model Selection — use request model or fall back to default
        String modelId = (request.getModelId() != null && !request.getModelId().isBlank())
                ? request.getModelId()
                : defaultModelId;

        // STEP 2: Build the user message
        // ContentBlock.fromText() wraps plain text into a Converse API content block
        ContentBlock userContent = ContentBlock.fromText(request.getPrompt());
        Message userMessage = Message.builder()
                .role(ConversationRole.USER)     // Must be USER or ASSISTANT
                .content(userContent)
                .build();

        // STEP 3: Assemble the Converse request
        ConverseRequest.Builder converseBuilder = ConverseRequest.builder()
                .modelId(modelId)
                .messages(userMessage);

        // STEP 4: Attach system prompt (if provided)
        // System prompts set the model's persona — applied BEFORE user messages
        if (request.getSystemPrompt() != null && !request.getSystemPrompt().isBlank()) {
            SystemContentBlock systemBlock = SystemContentBlock.fromText(request.getSystemPrompt());
            converseBuilder.system(systemBlock);
        }

        // STEP 5: Set inference parameters
        int maxTokens   = request.getMaxTokens()   != null ? request.getMaxTokens()   : defaultMaxTokens;
        double temperature = request.getTemperature() != null ? request.getTemperature() : defaultTemperature;
        double topP     = request.getTopP()        != null ? request.getTopP()        : 0.9;

        InferenceConfiguration inferenceConfig = InferenceConfiguration.builder()
                .maxTokens(maxTokens)
                .temperature((float) temperature)   // SDK uses float, not double
                .topP((float) topP)
                .build();

        converseBuilder.inferenceConfig(inferenceConfig);

        // STEP 6: ⭐ THE ACTUAL API CALL to AWS Bedrock
        // This is synchronous — waits for the complete response
        ConverseResponse converseResponse = bedrockRuntimeClient.converse(
                converseBuilder.build()
        );

        // STEP 7: Parse the response
        long latencyMs    = System.currentTimeMillis() - startTime;
        String responseText = extractResponseText(converseResponse);
        TokenUsage tokenUsage = converseResponse.usage();
        int inputTokens   = tokenUsage != null ? tokenUsage.inputTokens()  : 0;
        int outputTokens  = tokenUsage != null ? tokenUsage.outputTokens() : 0;
        String stopReason = converseResponse.stopReasonAsString();

        log.info("✅ Response | model={} | in={} out={} tokens | {}ms | stop={}",
                modelId, inputTokens, outputTokens, latencyMs, stopReason);

        // STEP 8: Return structured DTO
        return BedrockResponse.builder()
                .response(responseText)
                .modelId(modelId)
                .inputTokens(inputTokens)
                .outputTokens(outputTokens)
                .totalTokens(inputTokens + outputTokens)
                .latencyMs(latencyMs)
                .stopReason(stopReason)
                .build();
    }

    // ⭐ Response parsing: ConverseResponse → String
    // Structure: ConverseResponse → output → message → content[0] → text
    private String extractResponseText(ConverseResponse response) {
        Message assistantMessage = response.output().message();
        List<ContentBlock> contentBlocks = assistantMessage.content();

        StringBuilder responseText = new StringBuilder();
        for (ContentBlock block : contentBlocks) {
            if (block.text() != null) {
                responseText.append(block.text());  // Collect all text blocks
            }
        }
        return responseText.toString();
    }
}
```

**📌 Key Concepts — BedrockServiceImpl:**
| Step | AWS SDK Class | Purpose |
|------|--------------|---------|
| Build message | `Message.builder()` + `ContentBlock.fromText()` | Wraps user text into the Converse API message format |
| System prompt | `SystemContentBlock.fromText()` | Sets model persona, applied before user messages |
| Inference config | `InferenceConfiguration.builder()` | Controls temperature, maxTokens, topP |
| API call | `bedrockRuntimeClient.converse()` | Synchronous HTTP call to Bedrock — this is where tokens are consumed |
| Token tracking | `converseResponse.usage()` | Returns `inputTokens` and `outputTokens` for cost monitoring |
| Stop reason | `converseResponse.stopReasonAsString()` | `"end_turn"` = natural finish; `"max_tokens"` = truncated |

---

### 22.5 Rate Limiting — `RateLimitConfig.java`

**Purpose:** Protects the API from abuse and prevents cost explosions by limiting requests per minute using the **Token Bucket** algorithm.

```java
@Configuration
public class RateLimitConfig {

    @Value("${rate-limit.requests-per-minute:10}")  // Default: 10 req/min
    private int requestsPerMinute;

    @Bean
    public Bucket rateLimitBucket() {
        // Bandwidth.classic() = Token Bucket:
        //   capacity  → max burst size (how many tokens the bucket can hold)
        //   refill    → how fast tokens are refilled
        //
        // Refill.greedy() → tokens are added continuously (not in bulk at interval end)
        // This creates smooth traffic distribution instead of bursty refills.
        Bandwidth limit = Bandwidth.classic(
                requestsPerMinute,                                       // Bucket holds 10 tokens
                Refill.greedy(requestsPerMinute, Duration.ofMinutes(1))  // Refills 10/min = ~1 every 6s
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
```

**How it's used in the controller:**
```java
// In BedrockController and JobAnalysisController:
if (!rateLimitBucket.tryConsume(1)) {
    // Returns false immediately if no tokens available (non-blocking)
    throw new RateLimitExceededException("Rate limit exceeded. Please wait.");
    // → GlobalExceptionHandler maps this to HTTP 429 Too Many Requests
}
```

**📌 Token Bucket Analogy:**
```
Bucket = 10 tokens (capacity)

Request 1  → consume 1 → 9 tokens left  ✅
Request 2  → consume 1 → 8 tokens left  ✅
...
Request 10 → consume 1 → 0 tokens left  ✅
Request 11 → bucket empty → HTTP 429    ❌
After 6s   → 1 token refilled           ✅
```

> ⚠️ **Production Note:** This is a per-JVM in-memory limiter. For multi-instance deployments, use Redis-backed Bucket4j or AWS API Gateway rate limiting.

---

### 22.6 REST Controller — `BedrockController.java`

**Purpose:** Handles all HTTP requests — validates input, enforces rate limits, delegates to service, returns responses. **No business logic here.**

```java
@Slf4j
@RestController                 // = @Controller + @ResponseBody (auto-serializes return values to JSON)
@RequestMapping("/api/v1")      // All endpoints in this class are prefixed with /api/v1
@RequiredArgsConstructor        // Constructor injection for all final fields
public class BedrockController {

    private final BedrockService bedrockService;
    private final Bucket rateLimitBucket;           // Injected from RateLimitConfig

    // ⭐ POST /api/v1/chat — Main Bedrock endpoint
    @PostMapping("/chat")
    public ResponseEntity<BedrockResponse> chat(@Valid @RequestBody PromptRequest request) {
        //          ↑ @Valid    → triggers Jakarta Bean Validation (@NotBlank, @Size, etc.)
        //          ↑ @RequestBody → deserializes JSON body into PromptRequest

        if (!rateLimitBucket.tryConsume(1)) {       // Check rate limit before processing
            throw new RateLimitExceededException("Rate limit exceeded.");
        }

        log.info("📨 Chat request | model={} | prompt={} chars",
                request.getModelId() != null ? request.getModelId() : "default",
                request.getPrompt().length());       // Log length, NOT the full prompt (privacy)

        BedrockResponse response = bedrockService.chat(request);  // Delegate to service

        return ResponseEntity.ok(response);         // HTTP 200 + JSON body
    }

    // GET /api/v1/models — Returns map of supported model IDs
    @GetMapping("/models")
    public ResponseEntity<Map<String, String>> listModels() {
        Map<String, String> models = new LinkedHashMap<>();
        models.put("anthropic.claude-3-5-sonnet-20241022-v2:0", "Claude 3.5 Sonnet v2 — Best quality");
        models.put("anthropic.claude-3-5-haiku-20241022-v1:0",  "Claude 3.5 Haiku — Fast & cheap");
        models.put("amazon.titan-text-express-v1",               "Titan Text Express — AWS-native");
        models.put("meta.llama3-1-70b-instruct-v1:0",           "Llama 3.1 70B — Open source");
        // ... more models
        return ResponseEntity.ok(models);
    }

    // GET /api/v1/health — Used by load balancers and Kubernetes probes
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "bedrock-api",
            "version", "1.0.0"
        ));
    }
}
```

**📌 Clean Architecture Rule (from your project's own comment):**
```
Controller  → Handles HTTP only (validation, status codes, request/response)
Service     → Contains ALL business logic (Bedrock calls, parsing)
Config      → Creates and configures external clients (BedrockRuntimeClient)

Controller NEVER calls AWS SDK directly.
Service NEVER maps HTTP status codes.
```

---

### 22.7 MCP Job Analysis Controller — `JobAnalysisController.java`

**Purpose:** The star endpoint — demonstrates the complete MCP (Model Context Protocol) pattern: fetch DB data → format as context → inject into Bedrock prompt → return AI analysis.

```java
// POST /api/v1/jobs/analyze
@PostMapping("/analyze")
public ResponseEntity<BedrockResponse> analyzeJobs(@Valid @RequestBody JobAnalysisRequest request) {

    if (!rateLimitBucket.tryConsume(1)) {
        throw new RateLimitExceededException("Rate limit exceeded.");
    }

    // ─────────────────────────────────────────────────
    // STEP 1: Fetch relevant jobs from MySQL
    // ─────────────────────────────────────────────────
    List<Job> jobs = fetchFilteredJobs(request);
    // fetchFilteredJobs() applies priority filters: keyword > company > location > platform > all

    // ─────────────────────────────────────────────────
    // STEP 2: Build context string (the MCP "resource")
    // ─────────────────────────────────────────────────
    String context;
    if ("ANALYTICS".equalsIgnoreCase(request.getAnalysisType())) {
        context = jobService.buildAnalyticsSummary();           // Aggregated stats
    } else if (Boolean.TRUE.equals(request.getIncludeFullDescription())) {
        context = jobService.buildDetailedContextForBedrock(jobs); // Full descriptions (more tokens!)
    } else {
        context = jobService.buildContextForBedrock(jobs);      // Concise summaries (token-optimized)
    }

    // ─────────────────────────────────────────────────
    // STEP 3: Build the system + user prompt
    // ─────────────────────────────────────────────────
    String systemPrompt = """
            You are an expert Job Market Analyst and Career Advisor AI.
            You have access to a database of job listings from LinkedIn and Naukri platforms.
            
            Your responsibilities:
            1. Analyze job data accurately based on the provided database context
            2. Provide actionable career advice and recommendations
            3. Identify trends, patterns, and opportunities in the job market
            4. Highlight which jobs have less competition (fewer applicants)
            5. Suggest which jobs match specific skills or experience levels
            
            Always base your analysis on the ACTUAL data provided.
            Format your response with clear headers and bullet points.
            """;

    // ⭐ KEY: Combine DB context + user question into one prompt
    String combinedPrompt = String.format(
            "%s\n\n--- USER QUESTION ---\n%s",
            context, request.getQuestion()
    );

    // ─────────────────────────────────────────────────
    // STEP 4: Call AWS Bedrock via BedrockService
    // ─────────────────────────────────────────────────
    PromptRequest bedrockRequest = PromptRequest.builder()
            .modelId(request.getModelId())   // null → uses default (Claude 3 Sonnet)
            .prompt(combinedPrompt)
            .systemPrompt(systemPrompt)
            .maxTokens(4096)
            .temperature(0.5)   // ← Lower temp for ANALYTICAL accuracy (not creative)
            .topP(0.9)
            .build();

    BedrockResponse response = bedrockService.chat(bedrockRequest);

    return ResponseEntity.ok(response);
}

// Helper: applies filters in priority order
private List<Job> fetchFilteredJobs(JobAnalysisRequest request) {
    if (request.getKeyword() != null && !request.getKeyword().isBlank())
        return jobService.searchJobs(request.getKeyword());      // keyword first
    if (request.getCompany() != null && !request.getCompany().isBlank())
        return jobService.getJobsByCompany(request.getCompany());
    if (request.getLocation() != null && !request.getLocation().isBlank())
        return jobService.getJobsByLocation(request.getLocation());
    if (request.getPlatform() != null && !request.getPlatform().isBlank())
        return jobService.getJobsByPlatform(request.getPlatform());
    return jobService.getAllJobs();  // No filters → all jobs
}
```

**📌 MCP Pattern Traced End-to-End:**
```
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{"question": "Which Java jobs have the least competition?", "keyword": "Java"}'

  ↓ JobAnalysisController.analyzeJobs()
  ↓ fetchFilteredJobs() → jobService.searchJobs("Java")
  ↓ SQL: SELECT * FROM jobs WHERE title LIKE '%Java%' OR description LIKE '%Java%'
  ↓ buildContextForBedrock(jobs)  → "Job #1: Java Dev at TCS | Applicants: Less than 10..."
  ↓ combinedPrompt = context + question
  ↓ bedrockService.chat(combinedPrompt + systemPrompt)
  ↓ AWS Bedrock → Claude 3 Sonnet
  ↓ Returns: "Based on the job data, here are the 3 Java roles with least competition..."
```

---

### 22.8 MCP Context Builder — `JobService.java` (Key Methods)

**Purpose:** Converts raw database records into token-efficient text that Claude can read and analyze.

```java
@Slf4j @Service @RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    // ⭐ CORE MCP METHOD: Converts DB rows → structured context text
    public String buildContextForBedrock(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return "DATABASE CONTEXT: No jobs found in the database.";
        }

        StringBuilder context = new StringBuilder();
        context.append(String.format(
                "DATABASE CONTEXT — Jobs from linkedin_naukr_jobs.jobs table (%d records):\n\n",
                jobs.size()
        ));

        for (Job job : jobs) {
            // Each job becomes one concise line for Claude to parse
            context.append(job.toBedrockContext()).append("\n");
            // Example line:
            // "Job #1: Java Full Stack Developer at TCS | Location: Bengaluru |
            //  Posted: 1 day ago | Applicants: Less than 10 | Platform: Naukri | Applied: No"
        }

        log.debug("📝 Context built: {} jobs, {} chars", jobs.size(), context.length());
        return context.toString();
    }

    // ⚠️ Token-heavy method — use only when full description analysis is needed
    public String buildDetailedContextForBedrock(List<Job> jobs) {
        StringBuilder context = new StringBuilder();
        context.append(String.format("DETAILED DATABASE CONTEXT — %d records:\n\n", jobs.size()));

        for (Job job : jobs) {
            context.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            context.append(String.format("JOB #%d\nTitle: %s\nCompany: %s\nLocation: %s\n",
                    job.getId(), job.getTitle(), job.getCompany(), job.getLocation()));
            context.append(String.format("Posted: %s\nApplicants: %s\nPlatform: %s\n",
                    job.getJobPosted(), job.getJobApplyedCountStatus(), job.getPlatform()));
            context.append(String.format("Applied: %s\nURL: %s\nDescription:\n%s\n\n",
                    job.getApplied() == 1 ? "Yes" : "No",
                    job.getJobUrl(),
                    job.getDescription() != null ? job.getDescription() : "N/A"));
        }
        return context.toString();
    }

    // Analytics summary for high-level trend questions
    public String buildAnalyticsSummary() {
        long totalJobs        = jobRepository.count();
        List<Object[]> byCompany  = jobRepository.countJobsByCompany();
        List<Object[]> byLocation = jobRepository.countJobsByLocation();
        List<Job> unapplied   = jobRepository.findByApplied(0);

        StringBuilder summary = new StringBuilder("DATABASE ANALYTICS SUMMARY:\n\n");
        summary.append(String.format("Total Jobs: %d\nUnapplied Jobs: %d\n\n", totalJobs, unapplied.size()));
        summary.append("Jobs by Company:\n");
        for (Object[] row : byCompany)
            summary.append(String.format("  %s: %s jobs\n", row[0], row[1]));
        summary.append("\nJobs by Location:\n");
        for (Object[] row : byLocation)
            summary.append(String.format("  %s: %s jobs\n", row[0], row[1]));
        return summary.toString();
    }
}
```

---

### 22.9 Job Entity — `Job.java`

**Purpose:** Maps the MySQL `linkedin_naukr_jobs.jobs` table to a Java object. The `toBedrockContext()` method is critical — it controls token usage.

```java
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "jobs", catalog = "linkedin_naukr_jobs")  // catalog = database name in MySQL
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment PK
    private Long id;

    @Column(name = "title")           private String title;
    @Column(name = "company")         private String company;
    @Column(name = "location")        private String location;
    @Column(name = "job_posted")      private String jobPosted;
    @Column(name = "job_applyed_count_status") private String jobApplyedCountStatus;
    @Column(name = "job_url")         private String jobUrl;
    @Column(name = "platform")        private String platform;
    @Column(name = "description", columnDefinition = "TEXT") private String description;
    @Column(name = "applied")         private Integer applied;       // 0 = not applied, 1 = applied
    @Column(name = "created_at")      private LocalDateTime createdAt;

    // ⭐ KEY METHOD: Converts entity to a single-line context string for Bedrock
    // This is the "MCP serialization" step — converts DB object to AI-readable text
    // Token-optimized: ~150 chars per job vs 1000+ chars for full description
    public String toBedrockContext() {
        return String.format(
                "Job #%d: %s at %s | Location: %s | Posted: %s | Applicants: %s | Platform: %s | Applied: %s | URL: %s",
                id,
                title    != null ? title    : "N/A",
                company  != null ? company  : "N/A",
                location != null ? location : "N/A",
                jobPosted != null ? jobPosted : "N/A",
                jobApplyedCountStatus != null ? jobApplyedCountStatus : "N/A",
                platform != null ? platform : "N/A",
                applied != null && applied == 1 ? "Yes" : "No",
                jobUrl   != null ? jobUrl   : "N/A"
        );
    }
}
```

**📌 Token Optimization Insight:**
```
Full description: "We are looking for a Java developer with 5+ years of experience...
                  Requirements: Spring Boot, Microservices, AWS, Docker, Kubernetes..."
→ ~1000 characters = ~250 tokens per job

toBedrockContext() output: "Job #1: Java Developer at TCS | Location: Bengaluru | ..."
→ ~150 characters = ~38 tokens per job

With 50 jobs: 250 × 50 = 12,500 tokens vs 38 × 50 = 1,900 tokens
Savings: ~85% fewer tokens = ~85% lower cost for context injection!
```

---

### 22.10 Global Exception Handler — `GlobalExceptionHandler.java`

**Purpose:** Catches ALL exceptions thrown by controllers and converts them into consistent, secure `ErrorResponse` JSON objects. Never exposes stack traces to clients.

```java
@Slf4j
@RestControllerAdvice       // ← Intercepts exceptions from ALL @RestController classes globally
public class GlobalExceptionHandler {

    // ① Input validation failures (@Valid on request body)
    // Triggered when: blank prompt, maxTokens > 8192, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Collect all field errors into a readable message
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Bad Request")
                .message(errors)       // "prompt: Prompt is required; maxTokens: must not exceed 8192"
                .path(request.getRequestURI())
                .build()
        );
    }

    // ② Rate limit exceeded (Bucket4j)
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimitExceeded(...) {
        // Returns HTTP 429 Too Many Requests
    }

    // ③ AWS IAM Access Denied (model not enabled OR IAM policy missing bedrock:InvokeModel)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(...) {
        // Returns HTTP 403 Forbidden
        // Message: "Ensure the model is enabled and IAM permissions are correct."
    }

    // ④ AWS Bedrock throttling (exceeded per-account Bedrock quota)
    @ExceptionHandler(ThrottlingException.class)
    public ResponseEntity<ErrorResponse> handleThrottling(...) {
        // Returns HTTP 429 — but this is AWS throttling, not our rate limiter
    }

    // ⑤ Model timeout (model took > 60s to respond)
    @ExceptionHandler(ModelTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleModelTimeout(...) {
        // Returns HTTP 504 Gateway Timeout
        // Advice: reduce maxTokens or use a faster model (Haiku)
    }

    // ⑥ Catch-all — NEVER expose internal details to clients!
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        // Full stack trace in logs for debugging ↑

        return ResponseEntity.status(500).body(
            ErrorResponse.builder()
                .status(500)
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                // ↑ Generic message to client — never expose exception details!
                .path(request.getRequestURI())
                .build()
        );
    }
}
```

**📌 Exception → HTTP Status Mapping (from your project):**
| Exception | HTTP Status | Cause |
|-----------|------------|-------|
| `MethodArgumentNotValidException` | 400 Bad Request | `@Valid` failure on request body |
| `RateLimitExceededException` | 429 Too Many Requests | Local Bucket4j limit exceeded |
| `AccessDeniedException` (AWS) | 403 Forbidden | IAM policy missing or model not enabled |
| `ThrottlingException` (AWS) | 429 Too Many Requests | AWS Bedrock per-account quota exceeded |
| `ModelTimeoutException` (AWS) | 504 Gateway Timeout | Model response > 60s |
| `ValidationException` (AWS) | 400 Bad Request | Invalid parameters sent to Bedrock API |
| `BedrockApiException` (custom) | 502 Bad Gateway | Upstream Bedrock service error |
| `Exception` (catch-all) | 500 Internal Server Error | Unexpected errors |

---

### 22.11 Application Configuration — `application.yml`

**Purpose:** Centralizes all configurable settings — server, database, AWS credentials, rate limiting, logging. Uses profile-based overrides for dev vs. prod.

```yaml
# ─── SERVER ───────────────────────────────────────────
server:
  port: 8080
  tomcat:
    max-threads: 200          # Max concurrent requests
    connection-timeout: 30s

# ─── SPRING CORE ──────────────────────────────────────
spring:
  application:
    name: bedrock-api
  profiles:
    active: dev               # Switch to 'prod' for production

  jackson:
    serialization:
      write-dates-as-timestamps: false   # ISO-8601 date format
      indent-output: true                # Pretty JSON (disable in prod)
    default-property-inclusion: non_null # Omit null fields

  # ─── DATABASE (MySQL) ────────────────────────────────
  datasource:
    url: jdbc:mysql://localhost:3306/linkedin_naukr_jobs
    username: root
    password: ${DB_PASSWORD:2580}   # ← In prod: use env var DB_PASSWORD
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      minimum-idle: 2
      maximum-pool-size: 10

  # ─── JPA / HIBERNATE ─────────────────────────────────
  jpa:
    hibernate:
      ddl-auto: none          # NEVER auto-create tables in existing DB
    show-sql: true            # Log SQL (disable in production)
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

# ─── AWS BEDROCK ─────────────────────────────────────
aws:
  bedrock:
    region: ap-south-1
    # ⚠️ In production: remove these and use IAM Instance Profile / ECS Task Role
    access-key-id: ${AWS_ACCESS_KEY_ID:}
    secret-access-key: ${AWS_SECRET_ACCESS_KEY:}
    default-model: apac.anthropic.claude-3-sonnet-20240229-v1:0
    max-tokens: 4096
    temperature: 0.7

# ─── RATE LIMITING ───────────────────────────────────
rate-limit:
  requests-per-minute: 10

# ─── ACTUATOR (Health/Metrics) ───────────────────────
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: when-authorized

# ─── LOGGING ─────────────────────────────────────────
logging:
  level:
    root: INFO
    com.awsbedrock.api: DEBUG          # Your app — verbose
    software.amazon.awssdk: WARN       # AWS SDK — warnings only
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

**📌 Key Configuration Patterns:**
| Pattern | Example | Purpose |
|---------|---------|---------|
| Environment variable override | `${DB_PASSWORD:2580}` | `${VAR:default}` — uses env var if set, else default |
| Profile-based config | `profiles.active: dev` | Switch to `application-prod.yml` for production |
| `ddl-auto: none` | JPA Hibernate | Never let ORM modify your existing DB schema |
| AWS env var convention | `AWS_BEDROCK_REGION` → `aws.bedrock.region` | Spring Boot binds env vars to YAML keys automatically (underscores → dots, uppercase → lowercase) |

---

### 22.12 Complete End-to-End Request Flow (Your Project)

```
curl -X POST http://localhost:3000/jobs/analyze \
  -d '{"question": "Which Java jobs have least competition?", "keyword": "Java"}'

① JS Client (port 3000)
   └─ Forwards request to → Spring Boot (port 8080)

② Spring Boot: JobAnalysisController.analyzeJobs()
   ├─ @Valid validates JobAnalysisRequest fields
   ├─ rateLimitBucket.tryConsume(1) → checks token bucket (10/min)
   └─ fetchFilteredJobs(request)

③ JobService.searchJobs("Java")
   └─ jobRepository.searchByKeyword("Java")
      └─ SQL: SELECT * FROM jobs WHERE title LIKE '%Java%'
      └─ Returns: List<Job> (e.g., 4 Java jobs)

④ JobService.buildContextForBedrock(jobs)
   └─ Calls job.toBedrockContext() for each job
   └─ Returns:
      "DATABASE CONTEXT — 4 records:
       Job #1: Java Full Stack Developer at TCS | Location: Bengaluru | Applicants: Less than 10 | ...
       Job #2: Java Developer at Quadrant | Location: Hyderabad | Applicants: 100+ | ..."

⑤ JobAnalysisController builds combined prompt:
   systemPrompt = "You are an expert Job Market Analyst..."
   userPrompt   = context + "\n\n--- USER QUESTION ---\nWhich Java jobs have least competition?"

⑥ BedrockServiceImpl.chat(bedrockRequest)
   ├─ Builds ConverseRequest (modelId, message, systemPrompt, inferenceConfig)
   └─ bedrockRuntimeClient.converse(request)
      └─ HTTPS + SigV4 → AWS Bedrock API → Claude 3 Sonnet (apac region)

⑦ AWS Bedrock returns ConverseResponse
   ├─ output.message.content[0].text = "Based on the database..."
   ├─ usage.inputTokens  = 425
   ├─ usage.outputTokens = 312
   └─ stopReason         = "end_turn"

⑧ BedrockServiceImpl builds BedrockResponse DTO
   └─ { response: "...", inputTokens: 425, outputTokens: 312, latencyMs: 3240, stopReason: "end_turn" }

⑨ HTTP 200 OK → JS Client → curl output
```

---

*Project Source: `E:\Teja_Interview_preparation\Claude_Preparation\MCP-Model Context Protocol\Claude with Amazon Bedrock\AWS_BedrockProject_withClaude\bedrock-api`*
*Generated for: Claude Architect Certification Preparation*
*Source: Claude with Amazon Bedrock learning materials + AWS BedrockProject_withClaude source code & documentation*
*Covers: AWS Bedrock API · Claude Models · Tool Use · RAG · MCP · Prompt Evaluation · Security · Deployment*
