# AWS Bedrock + Spring Boot Microservices — Complete Guide

> **Production-ready Java Spring Boot backend integrated with AWS Bedrock Foundation Models,
> MySQL Database, MCP (Model Context Protocol) patterns, and JavaScript Client Microservice.**

---

## Table of Contents

1. [How AWS Bedrock Works Internally](#1-how-aws-bedrock-works-internally)
2. [Bedrock vs OpenAI APIs — Comparison](#2-bedrock-vs-openai-apis--comparison)
3. [Best Models for Each Use Case](#3-best-models-for-each-use-case)
4. [Project Architecture & Folder Structure](#4-project-architecture--folder-structure)
5. [Microservice Communication Flow](#5-microservice-communication-flow)
6. [MCP + Database Integration](#6-mcp--database-integration)
7. [IAM Permissions Required](#7-iam-permissions-required)
8. [AWS Credentials Configuration](#8-aws-credentials-configuration)
9. [Step-by-Step Configuration Guide](#9-step-by-step-configuration-guide)
10. [Converse API Deep Dive](#10-converse-api-deep-dive)
11. [Sample curl Commands](#11-sample-curl-commands)
12. [Postman Request Examples](#12-postman-request-examples)
13. [Deployment Options (EC2, ECS, Lambda)](#13-deployment-options)
14. [Cost Optimization & Free Tier](#14-cost-optimization--free-tier)
15. [Security Best Practices](#15-security-best-practices)
16. [Rate Limiting](#16-rate-limiting)
17. [Docker & Kubernetes Deployment](#17-docker--kubernetes-deployment)

---

## 1. How AWS Bedrock Works Internally

### What is AWS Bedrock?

AWS Bedrock is a **fully managed service** that provides access to foundation models (FMs) from leading AI companies through a single API. You don't manage any infrastructure — no GPU clusters, no model weights, no scaling.

### Internal Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                        YOUR APPLICATION                             │
│  (Spring Boot + AWS SDK v2)                                        │
└──────────────────────┬──────────────────────────────────────────────┘
                       │ HTTPS (TLS 1.2+)
                       │ SigV4 Authentication
                       ▼
┌─────────────────────────────────────────────────────────────────────┐
│                     AWS BEDROCK SERVICE                             │
│                                                                     │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────────────┐   │
│  │ API Gateway  │──│ Auth & IAM   │──│ Model Router             │   │
│  │ (Endpoints)  │  │ (SigV4)      │  │ (Routes to correct FM)   │   │
│  └─────────────┘  └──────────────┘  └──────────────────────────┘   │
│                                              │                      │
│                        ┌─────────────────────┼──────────────┐       │
│                        ▼                     ▼              ▼       │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌───────────┐ │
│  │  Anthropic    │ │  Amazon      │ │  Meta         │ │ Mistral   │ │
│  │  Claude 3.5   │ │  Titan       │ │  Llama 3      │ │ Large     │ │
│  │  (Hosted by   │ │  (AWS-native │ │  (Hosted by   │ │ (Hosted)  │ │
│  │   AWS)        │ │   model)     │ │   AWS)        │ │           │ │
│  └──────────────┘ └──────────────┘ └──────────────┘ └───────────┘ │
│                                                                     │
│  KEY PROPERTIES:                                                    │
│  ✅ Models run in AWS infrastructure (not provider's)              │
│  ✅ Your data never leaves AWS (stays in your region)              │
│  ✅ No model training on your data                                 │
│  ✅ Pay-per-token (no upfront commitment)                          │
│  ✅ Serverless — auto-scales with demand                           │
└─────────────────────────────────────────────────────────────────────┘
```

### How a Bedrock API Call Works (Step by Step)

1. **Your App** sends an HTTPS request with SigV4 signature
2. **API Gateway** receives the request, validates TLS
3. **IAM Authentication** verifies your credentials and permissions
4. **Model Router** identifies the target model from the request's `modelId`
5. **Inference Engine** loads the model (pre-warmed), processes the prompt
6. **Token Generation** — model generates tokens one by one (or streamed)
7. **Response** sent back with generated text + usage metadata

### Key Bedrock APIs

| API | Purpose | When to Use |
|-----|---------|-------------|
| `Converse` | Unified chat API (model-agnostic) | ✅ **Always use this** — works with ALL models |
| `ConverseStream` | Streaming version of Converse | Real-time token-by-token output |
| `InvokeModel` | Low-level API (model-specific JSON) | Only if Converse doesn't support a feature |
| `ListFoundationModels` | List available models | Discovering models in your region |

---

## 2. Bedrock vs OpenAI APIs — Comparison

| Feature | AWS Bedrock | OpenAI API |
|---------|------------|------------|
| **Model Providers** | Anthropic, Meta, Amazon, Mistral, Cohere, AI21, Stability AI | OpenAI only (GPT-4, GPT-3.5, DALL-E) |
| **Authentication** | AWS IAM (SigV4) | API Key (Bearer token) |
| **Data Privacy** | Data stays in your AWS region, never used for training | Data may be used for training (unless opted out) |
| **API Style** | `Converse` (unified) or `InvokeModel` (model-specific) | Chat Completions API |
| **Pricing Model** | Pay per token (input/output priced separately) | Pay per token (input/output priced separately) |
| **Free Tier** | Some models have free tier for first 3 months | $5 credit for new accounts |
| **Infrastructure** | Serverless, auto-scaling, managed | Serverless, managed |
| **SDK** | AWS SDK v2 (Java, Python, JS, etc.) | OpenAI SDK (Python, Node.js) |
| **Streaming** | `ConverseStream` API | Server-Sent Events (SSE) |
| **Embeddings** | Titan Embeddings, Cohere Embed | text-embedding-ada-002 |
| **Fine-tuning** | Supported for select models | GPT-3.5 and GPT-4 fine-tuning |
| **Region Availability** | Multiple AWS regions | Global (OpenAI manages) |
| **Compliance** | SOC 2, HIPAA, FedRAMP (via AWS) | SOC 2 |
| **Rate Limits** | Per-account, per-model (configurable) | Per-org, per-model (fixed tiers) |
| **Best For** | Enterprise, regulated industries, multi-model | Rapid prototyping, GPT-specific features |

### When to Choose Bedrock
- Your organisation is already on AWS
- Data privacy/residency is critical (healthcare, finance)
- You need multiple model providers (not locked into one)
- You need enterprise compliance (HIPAA, SOC 2, FedRAMP)

### When to Choose OpenAI
- You want the latest GPT models immediately
- Simpler API key authentication is preferred
- You need DALL-E or Whisper (not available on Bedrock)
- Rapid prototyping without AWS setup

---

## 3. Best Models for Each Use Case

### Coding

| Model | Strengths | Recommendation |
|-------|-----------|----------------|
| **Claude 3.5 Sonnet v2** | Best code generation, debugging, refactoring | ⭐ **Top Pick** |
| Claude 3 Opus | Complex architecture decisions | For difficult problems |
| Llama 3.1 70B | Good open-source alternative | Budget-friendly |

### Chat / Conversational

| Model | Strengths | Recommendation |
|-------|-----------|----------------|
| **Claude 3.5 Sonnet v2** | Natural, helpful, follows instructions well | ⭐ **Top Pick** |
| Claude 3.5 Haiku | Fast responses, lower cost | High-volume chat |
| Mistral Large | Strong multilingual support | European languages |

### Embeddings (for RAG, Search, Similarity)

| Model | Dimensions | Recommendation |
|-------|-----------|----------------|
| **Titan Embeddings V2** | 1024 | ⭐ **Top Pick** (AWS-native, cheapest) |
| Cohere Embed English V3 | 1024 | Best accuracy for English |
| Cohere Embed Multilingual V3 | 1024 | Best for multilingual |

### RAG (Retrieval-Augmented Generation)

| Component | Recommended Model |
|-----------|------------------|
| **Embedding** | Titan Embeddings V2 |
| **Generation** | Claude 3.5 Sonnet v2 |
| **Vector Store** | Amazon OpenSearch / Pinecone / pgvector |
| **Orchestration** | Bedrock Knowledge Bases (managed RAG) |

---

## 4. Project Architecture & Folder Structure

```
AWS_BedrockProject_withClaude/
│
├── bedrock-api/                          # 🟢 Spring Boot Microservice
│   ├── pom.xml                           # Maven dependencies
│   ├── Dockerfile                        # Multi-stage Docker build
│   ├── k8s/
│   │   ├── deployment.yaml               # Kubernetes Deployment + Secrets
│   │   └── service.yaml                  # Kubernetes Service
│   └── src/
│       ├── main/
│       │   ├── java/com/awsbedrock/api/
│       │   │   ├── BedrockApiApplication.java     # Main entry point
│       │   │   ├── config/
│       │   │   │   ├── AwsBedrockConfig.java      # BedrockRuntimeClient bean
│       │   │   │   ├── RateLimitConfig.java        # Bucket4j rate limiting
│       │   │   │   └── WebConfig.java              # CORS configuration
│       │   │   ├── controller/
│       │   │   │   ├── BedrockController.java      # Chat & models endpoints
│       │   │   │   └── JobAnalysisController.java  # 🆕 MCP job analysis
│       │   │   ├── dto/
│       │   │   │   ├── PromptRequest.java          # Chat request DTO
│       │   │   │   ├── BedrockResponse.java        # Chat response DTO
│       │   │   │   ├── ErrorResponse.java          # Error envelope DTO
│       │   │   │   └── JobAnalysisRequest.java     # 🆕 Job analysis DTO
│       │   │   ├── entity/
│       │   │   │   └── Job.java                    # 🆕 JPA entity for jobs table
│       │   │   ├── repository/
│       │   │   │   └── JobRepository.java          # 🆕 Spring Data JPA
│       │   │   ├── service/
│       │   │   │   ├── BedrockService.java         # Interface
│       │   │   │   ├── BedrockServiceImpl.java     # Converse API logic
│       │   │   │   └── JobService.java             # 🆕 Job data + MCP context
│       │   │   ├── exception/
│       │   │   │   ├── GlobalExceptionHandler.java # Centralised error handling
│       │   │   │   ├── BedrockApiException.java    # Custom exception
│       │   │   │   └── RateLimitExceededException.java
│       │   │   └── util/
│       │   │       └── ModelRegistry.java          # Model ID constants
│       │   └── resources/
│       │       ├── application.yml                 # Main config + MySQL
│       │       ├── application-dev.yml             # Dev profile
│       │       └── application-prod.yml            # Prod profile
│       └── test/
│           └── java/com/awsbedrock/api/
│               └── BedrockApiApplicationTests.java
│
├── bedrock-js-client/                    # 🔵 JavaScript Client Microservice
│   ├── package.json
│   ├── Dockerfile
│   ├── .env.example
│   └── src/
│       ├── index.js                      # Express server
│       ├── config.js                     # Configuration
│       ├── routes/
│       │   └── chatRoutes.js             # Route handlers
│       └── services/
│           └── bedrockClient.js          # Axios HTTP client
│
└── docs/
    └── README.md                         # 📖 This file
```

---

## 5. Microservice Communication Flow

```
┌──────────────────────────────────────────────────────────────────────────────────┐
│                           COMPLETE SYSTEM ARCHITECTURE                          │
│                                                                                  │
│  ┌───────────┐     ┌───────────────────┐     ┌──────────────┐     ┌──────────┐ │
│  │           │     │  JS Client         │     │ Spring Boot  │     │  AWS     │ │
│  │ Browser/  │────▶│  Microservice      │────▶│ Bedrock API  │────▶│ Bedrock  │ │
│  │ Postman   │◀────│  (Express:3000)    │◀────│ (Tomcat:8080)│◀────│ (Claude) │ │
│  │           │     │                    │     │              │     │          │ │
│  └───────────┘     └───────────────────┘     └──────┬───────┘     └──────────┘ │
│                                                      │                          │
│                    HTTP/JSON                    JPA/JDBC                         │
│                    Communication               Connection                       │
│                                                      │                          │
│                                               ┌──────▼───────┐                  │
│                                               │  MySQL        │                  │
│                                               │  Database     │                  │
│                                               │              │                  │
│                                               │ linkedin_    │                  │
│                                               │ naukr_jobs   │                  │
│                                               │  .jobs       │                  │
│                                               └──────────────┘                  │
└──────────────────────────────────────────────────────────────────────────────────┘
```

### Request Flow (Example: "Analyze Java jobs")

```
1. User → POST http://localhost:3000/jobs/analyze
   Body: { "question": "Which Java jobs should I apply to?", "keyword": "Java" }

2. JS Client → Validates request, forwards to Spring Boot API
   POST http://localhost:8080/api/v1/jobs/analyze

3. Spring Boot → JobAnalysisController receives request
   ├── 3a. Rate limit check (Bucket4j)
   ├── 3b. JobService.searchJobs("Java") → SQL: SELECT * FROM jobs WHERE title ILIKE '%Java%'
   ├── 3c. JobService.buildContextForBedrock(jobs) → Converts DB rows to text context
   ├── 3d. Builds combined prompt: system + context + question
   └── 3e. BedrockService.chat(combinedPrompt) → AWS SDK call

4. AWS Bedrock → Receives Converse API request
   ├── 4a. Authenticates via IAM (SigV4)
   ├── 4b. Routes to Claude 3.5 Sonnet
   ├── 4c. Model generates response
   └── 4d. Returns response + token usage

5. Spring Boot → Parses response, builds BedrockResponse DTO

6. JS Client → Forwards response to browser

7. User sees: AI analysis of Java jobs from the database
```

---

## 6. MCP + Database Integration

### What is MCP (Model Context Protocol)?

MCP is a standard protocol (created by Anthropic) that lets AI models access external data sources in a structured way. Think of it as a "USB port" for AI — any data source can plug in.

### How We Implement MCP Concepts

```
Traditional Approach (without MCP):
  User types question → AI guesses answers (no real data)

MCP Approach (this project):
  User types question → App fetches REAL data from DB → Injects as context → AI analyzes REAL data

Example:
  Question: "Which jobs have the least competition?"
  
  Without MCP: "I don't have access to specific job listings..."
  
  With MCP:    "Based on the 4 jobs in your database:
                Job #1 (Scala Developer at TCS) has the least competition
                with 'Less than 10' applicants. I recommend applying to this one..."
```

### Database Schema

```sql
-- Your existing table
SELECT * FROM linkedin_naukr_jobs.jobs;

-- Columns:
-- id                        | BIGINT (PK)   | Auto-increment
-- title                     | VARCHAR       | "Scala Developer"
-- company                   | VARCHAR       | "Tata Consultancy Services"
-- location                  | VARCHAR       | "Bengaluru"
-- job_posted                | VARCHAR       | "Posted: 1 day ago"
-- job_applyed_count_status  | VARCHAR       | "Applicants: Less than 10"
-- job_url                   | VARCHAR       | https://naukri.com/...
-- platform                  | VARCHAR       | "Naukri"
-- description               | TEXT          | Full job description
-- applied                   | INTEGER       | 0 or 1
-- created_at                | TIMESTAMP     | 2026-05-19 13:17:10

-- Sample data from your database:
-- ID 1: Scala Developer at TCS, Bengaluru, <10 applicants
-- ID 2: Java Developer Apache Camel at Sciens, Chennai/Bengaluru/Noida, 100+ applicants
-- ID 3: Java Full Stack Developer at Quadrant, Hyderabad/Bengaluru, 100+ applicants
-- ID 4: React JS Developer at TCS, Hyderabad/Chennai/Bengaluru, 100+ applicants
```

---

## 7. IAM Permissions Required

### Minimum IAM Policy for Bedrock

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "BedrockInvokeModels",
            "Effect": "Allow",
            "Action": [
                "bedrock:InvokeModel",
                "bedrock:InvokeModelWithResponseStream"
            ],
            "Resource": [
                "arn:aws:bedrock:us-east-1::foundation-model/anthropic.claude-3-5-sonnet-*",
                "arn:aws:bedrock:us-east-1::foundation-model/anthropic.claude-3-*",
                "arn:aws:bedrock:us-east-1::foundation-model/amazon.titan-*",
                "arn:aws:bedrock:us-east-1::foundation-model/meta.llama3-*",
                "arn:aws:bedrock:us-east-1::foundation-model/mistral.*"
            ]
        },
        {
            "Sid": "BedrockListModels",
            "Effect": "Allow",
            "Action": [
                "bedrock:ListFoundationModels",
                "bedrock:GetFoundationModel"
            ],
            "Resource": "*"
        }
    ]
}
```

### Full Access Policy (Development Only!)

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": "bedrock:*",
            "Resource": "*"
        }
    ]
}
```

> ⚠️ **Never use full access in production.** Always follow the principle of least privilege.

### How to Attach the Policy

```bash
# Create the policy
aws iam create-policy \
  --policy-name BedrockInvokePolicy \
  --policy-document file://bedrock-policy.json

# Attach to a user
aws iam attach-user-policy \
  --user-name your-user \
  --policy-arn arn:aws:iam::123456789:policy/BedrockInvokePolicy

# Or attach to a role (for EC2/ECS/Lambda)
aws iam attach-role-policy \
  --role-name your-role \
  --policy-arn arn:aws:iam::123456789:policy/BedrockInvokePolicy
```

---

## 8. AWS Credentials Configuration

### Method 1: Environment Variables (Recommended for Development)

```bash
# Windows PowerShell
$env:AWS_ACCESS_KEY_ID = "AKIAIOSFODNN7EXAMPLE"
$env:AWS_SECRET_ACCESS_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
$env:AWS_REGION = "us-east-1"

# Linux/Mac
export AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE
export AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
export AWS_REGION=us-east-1
```

### Method 2: AWS Credentials File

```ini
# File: ~/.aws/credentials (Linux/Mac) or %USERPROFILE%\.aws\credentials (Windows)

[default]
aws_access_key_id = AKIAIOSFODNN7EXAMPLE
aws_secret_access_key = wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

[bedrock-dev]
aws_access_key_id = AKIA_DEV_KEY
aws_secret_access_key = DEV_SECRET_KEY

# To use a specific profile:
# export AWS_PROFILE=bedrock-dev
```

### Method 3: AWS SSO (Single Sign-On)

```bash
# Configure SSO
aws configure sso
# Follow the prompts to set up SSO

# Login
aws sso login --profile your-sso-profile

# Export profile
export AWS_PROFILE=your-sso-profile
```

### Method 4: IAM Instance Profile (EC2 — Best for Production)

```
No configuration needed in code!
1. Create IAM Role with Bedrock permissions
2. Attach role to EC2 instance
3. DefaultCredentialsProvider automatically picks it up
```

### Method 5: ECS Task Role (Best for Containers)

```
1. Create IAM Role with Bedrock permissions
2. Assign as "Task Role" in ECS Task Definition
3. SDK automatically uses the task role credentials
```

### Credential Provider Chain (Order)

```
DefaultCredentialsProvider checks (in order):
  1. Java System Properties
  2. Environment Variables          ← Development
  3. AWS credentials file           ← Development
  4. AWS config file (SSO)          ← SSO users
  5. ECS Container Credentials     ← ECS/Fargate
  6. EC2 Instance Profile           ← EC2 production
```

---

## 9. Step-by-Step Configuration Guide

### Step 1: Enable Models in Bedrock Console

```
1. Go to AWS Console → Amazon Bedrock
2. Click "Model access" in the left sidebar
3. Click "Manage model access"
4. Check the models you want:
   ✅ Anthropic Claude 3.5 Sonnet
   ✅ Anthropic Claude 3 Haiku
   ✅ Amazon Titan Text
   ✅ Meta Llama 3
5. Click "Request model access"
6. Wait for approval (usually instant for most models)
```

### Step 2: Set Up Credentials

Follow Method 1 or 2 from Section 8 above.

### Step 3: Configure MySQL

```bash
# Ensure MySQL is running with the linkedin_naukr_jobs database
# The jobs table should already exist with your data

# Verify connection:
mysql -h localhost -u root -p -D linkedin_naukr_jobs -e "SELECT COUNT(*) FROM jobs;"
```

### Step 4: Update application.yml

```yaml
# Already configured! Key values to verify:
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/linkedin_naukr_jobs
    username: root
    password: your_actual_password  # CHANGE THIS

aws:
  bedrock:
    region: us-east-1               # Must match where models are enabled
    default-model: anthropic.claude-3-5-sonnet-20241022-v2:0
```

### Step 5: Run the Application

```bash
# From the bedrock-api directory:
cd bedrock-api

# Option 1: Maven
mvn spring-boot:run

# Option 2: Build & run JAR
mvn clean package -DskipTests
java -jar target/bedrock-api-1.0.0.jar

# Option 3: With specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Step 6: Run the JS Client (Optional)

```bash
cd bedrock-js-client
npm install
npm start
```

---

## 10. Converse API Deep Dive

### Why Converse API?

Before Converse, each model required a **different JSON payload**:

```java
// OLD WAY — InvokeModel (different JSON for each model!)

// Claude required:
{ "anthropic_version": "bedrock-2023-05-31", "messages": [...] }

// Titan required:
{ "inputText": "...", "textGenerationConfig": {...} }

// Llama required:
{ "prompt": "...", "max_gen_len": 512 }
```

```java
// NEW WAY — Converse API (SAME code for ALL models!)
ConverseRequest.builder()
    .modelId("ANY-MODEL-ID-HERE")   // Works with Claude, Titan, Llama, etc.
    .messages(userMessage)
    .inferenceConfig(config)
    .build();
```

### Converse API Request Structure

```java
ConverseRequest request = ConverseRequest.builder()
    // Which model to use
    .modelId("anthropic.claude-3-5-sonnet-20241022-v2:0")
    
    // Conversation messages (user + assistant turns)
    .messages(
        Message.builder()
            .role(ConversationRole.USER)
            .content(ContentBlock.fromText("Hello, explain Java streams"))
            .build()
    )
    
    // System prompt (persona/instructions)
    .system(SystemContentBlock.fromText("You are a Java expert"))
    
    // Inference parameters
    .inferenceConfig(InferenceConfiguration.builder()
        .maxTokens(4096)
        .temperature(0.7f)
        .topP(0.9f)
        .build())
    
    .build();
```

### Converse API Response Structure

```java
ConverseResponse response = client.converse(request);

// Get the assistant's text response
String text = response.output().message().content().get(0).text();

// Get token usage
int inputTokens = response.usage().inputTokens();
int outputTokens = response.usage().outputTokens();

// Get stop reason
String stopReason = response.stopReasonAsString();
// "end_turn" → model finished normally
// "max_tokens" → hit token limit (response truncated!)
```

---

## 11. Sample curl Commands

### Chat with Bedrock (via Spring Boot API)

```bash
# Basic chat
curl -X POST http://localhost:8080/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Explain microservices architecture in simple terms",
    "maxTokens": 512,
    "temperature": 0.7
  }'

# Chat with specific model
curl -X POST http://localhost:8080/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Write a Java Spring Boot REST controller for a todo app",
    "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
    "systemPrompt": "You are a senior Java developer. Always provide production-ready code.",
    "maxTokens": 2048,
    "temperature": 0.3
  }'

# Chat using Llama model
curl -X POST http://localhost:8080/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "What is the difference between REST and GraphQL?",
    "modelId": "meta.llama3-1-70b-instruct-v1:0",
    "maxTokens": 1024
  }'
```

### Job Analysis (MCP + Database)

```bash
# Analyze all jobs — which to apply to
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Based on the job data, which jobs have the least competition and I should apply to first? Rank them by priority."
  }'

# Analyze Java jobs with full descriptions
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What are the common skills required across all Java developer roles? Create a skills checklist.",
    "keyword": "Java",
    "includeFullDescription": true
  }'

# Analyze by company
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Compare the job offerings from this company. Which role seems best for a mid-level developer?",
    "company": "TCS"
  }'

# Get analytics summary
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "question": "Give me a complete hiring trends report with recommendations",
    "analysisType": "ANALYTICS"
  }'

# Analyze by location
curl -X POST http://localhost:8080/api/v1/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What job opportunities are available here? Which pays best?",
    "location": "Bengaluru"
  }'
```

### Job CRUD Endpoints

```bash
# List all jobs
curl http://localhost:8080/api/v1/jobs

# Get single job
curl http://localhost:8080/api/v1/jobs/1

# Search jobs by keyword
curl "http://localhost:8080/api/v1/jobs/search?keyword=Java"

# Get job statistics
curl http://localhost:8080/api/v1/jobs/stats

# List available models
curl http://localhost:8080/api/v1/models

# Health check
curl http://localhost:8080/api/v1/health
```

### Via JS Client (port 3000)

```bash
# Same endpoints, different port
curl -X POST http://localhost:3000/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt": "Hello, what is Spring Boot?"}'

curl -X POST http://localhost:3000/jobs/analyze \
  -H "Content-Type: application/json" \
  -d '{"question": "Which jobs should I apply to?"}'

curl http://localhost:3000/health
```

---

## 12. Postman Request Examples

### Collection Setup

1. Create a new Collection: "Bedrock API"
2. Set base URL variable: `{{baseUrl}}` = `http://localhost:8080`

### Request 1: Chat

```
Method: POST
URL: {{baseUrl}}/api/v1/chat
Headers:
  Content-Type: application/json
Body (raw JSON):
{
    "prompt": "Explain AWS Bedrock in 3 sentences",
    "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
    "systemPrompt": "You are a cloud computing expert",
    "maxTokens": 512,
    "temperature": 0.5,
    "topP": 0.9
}

Expected Response (200 OK):
{
    "response": "AWS Bedrock is a fully managed service...",
    "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
    "inputTokens": 28,
    "outputTokens": 145,
    "totalTokens": 173,
    "latencyMs": 2341,
    "stopReason": "end_turn"
}
```

### Request 2: Job Analysis

```
Method: POST
URL: {{baseUrl}}/api/v1/jobs/analyze
Headers:
  Content-Type: application/json
Body (raw JSON):
{
    "question": "Analyze these job listings and recommend which ones I should apply to as a Java developer with 3 years of experience. Consider competition level and skill match.",
    "keyword": "Java",
    "includeFullDescription": true
}

Expected Response (200 OK):
{
    "response": "Based on the job data from your database, here is my analysis...",
    "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
    "inputTokens": 2500,
    "outputTokens": 800,
    "totalTokens": 3300,
    "latencyMs": 5200,
    "stopReason": "end_turn"
}
```

---

## 13. Deployment Options

### Option 1: EC2 (Elastic Compute Cloud)

```
Best for: Full control, long-running processes, predictable workloads

Architecture:
  EC2 Instance (t3.medium) → Spring Boot JAR → Bedrock API
  + Application Load Balancer for HTTPS

Steps:
  1. Launch EC2 instance (Amazon Linux 2023, t3.medium)
  2. Install Java 17: sudo yum install java-17-amazon-corretto
  3. Create IAM Role with Bedrock permissions → Attach to EC2
  4. Upload JAR: scp target/bedrock-api-1.0.0.jar ec2-user@IP:/app/
  5. Run: java -jar /app/bedrock-api-1.0.0.jar --spring.profiles.active=prod
  6. Set up systemd service for auto-restart
  7. Configure ALB for HTTPS termination

Pros:
  ✅ Full control over the instance
  ✅ Easy to debug (SSH access)
  ✅ Predictable pricing
  ✅ IAM Instance Profile (no credentials in code)

Cons:
  ❌ You manage OS patches, scaling, monitoring
  ❌ Pay even when idle
  ❌ Manual scaling
```

### Option 2: ECS (Elastic Container Service) — Recommended

```
Best for: Container-based deployments, auto-scaling, production workloads

Architecture:
  ALB → ECS Service (Fargate) → Docker Container → Bedrock API

Steps:
  1. Build Docker image: docker build -t bedrock-api .
  2. Push to ECR: docker push 123456789.dkr.ecr.us-east-1.amazonaws.com/bedrock-api
  3. Create ECS Cluster (Fargate launch type)
  4. Create Task Definition with:
     - Container image from ECR
     - Task Role with Bedrock permissions
     - Port mapping: 8080
     - Resource limits: 1 vCPU, 2GB RAM
  5. Create ECS Service with:
     - Desired count: 2 (for HA)
     - ALB health check: /api/v1/health
     - Auto-scaling: CPU > 70%

Pros:
  ✅ Managed container orchestration
  ✅ Auto-scaling built-in
  ✅ No server management (Fargate)
  ✅ Rolling deployments
  ✅ Task Role for credentials (most secure)

Cons:
  ❌ More complex setup than EC2
  ❌ Slightly higher cost than EC2 for constant workloads
```

### Option 3: Lambda (Serverless)

```
Best for: Sporadic traffic, cost optimization, event-driven

Architecture:
  API Gateway → Lambda Function → Bedrock API

Limitations for Bedrock:
  ⚠️ Lambda timeout: 15 minutes max (Bedrock calls can take 30-60s)
  ⚠️ Cold starts: 5-15s for Java Spring Boot (consider SnapStart)
  ⚠️ Memory: Up to 10GB (usually 2-4GB is enough)

Steps:
  1. Use Spring Cloud Function or AWS SAM
  2. Package as a Lambda deployment package
  3. Configure API Gateway as trigger
  4. Set timeout: 120 seconds
  5. Set memory: 2048 MB
  6. Use Lambda execution role with Bedrock permissions
  7. Enable SnapStart for faster cold starts

Pros:
  ✅ Pay only when invoked (zero cost when idle)
  ✅ Auto-scales to thousands of concurrent executions
  ✅ No infrastructure management
  ✅ Best for sporadic/unpredictable traffic

Cons:
  ❌ Cold start latency (5-15s for Java)
  ❌ 15-minute timeout limit
  ❌ 10GB memory limit
  ❌ Not ideal for long-running Bedrock calls
```

---

## 14. Cost Optimization & Free Tier

### AWS Bedrock Free Tier (First 3 Months)

| Model | Free Tier Allowance |
|-------|-------------------|
| Amazon Titan Text Lite | 300M input tokens + 300M output tokens |
| Amazon Titan Text Express | 100M input tokens + 100M output tokens |
| Amazon Titan Embeddings | 100M input tokens |
| Anthropic Claude 3 Haiku | 100K input tokens + 100K output tokens |

### Pricing Comparison (per 1M tokens)

| Model | Input Price | Output Price | Cost per 1000 calls* |
|-------|------------|-------------|---------------------|
| Claude 3 Haiku | $0.25 | $1.25 | ~$0.15 |
| Claude 3.5 Haiku | $0.80 | $4.00 | ~$0.48 |
| Claude 3.5 Sonnet | $3.00 | $15.00 | ~$1.80 |
| Claude 3 Opus | $15.00 | $75.00 | ~$9.00 |
| Titan Text Lite | $0.15 | $0.20 | ~$0.04 |
| Titan Text Express | $0.20 | $0.60 | ~$0.08 |
| Llama 3.1 8B | $0.22 | $0.22 | ~$0.04 |
| Llama 3.1 70B | $0.72 | $0.72 | ~$0.14 |

*Estimated for 100 input + 200 output tokens per call

### 🔑 Troubleshooting AWS Credentials in PowerShell

If you are using environment variables to authenticate with AWS Bedrock during development, you may need to verify or clear your credentials. 

#### Checking Active Credentials
To check if the environment variables are currently set in your active PowerShell window, you can "echo" them out by typing:

```powershell
$env:AWS_ACCESS_KEY_ID
$env:AWS_SECRET_ACCESS_KEY
$env:AWS_REGION
```
*(If it prints a blank line, the variable is not set in that specific terminal session).*

#### Clearing Credentials
If you want to remove them from your current PowerShell session (for example, if you pasted the wrong key by accident), you can simply set them to an empty string:

```powershell
$env:AWS_ACCESS_KEY_ID=""
$env:AWS_SECRET_ACCESS_KEY=""
$env:AWS_REGION=""
```

Alternatively, you can completely delete the variables using the `Remove-Item` command:

```powershell
Remove-Item Env:\AWS_ACCESS_KEY_ID
Remove-Item Env:\AWS_SECRET_ACCESS_KEY
Remove-Item Env:\AWS_REGION
```

### Cost Optimization Tips

1. **Use the cheapest model that meets your needs**
   - Simple Q&A → Titan Text Lite or Llama 3.1 8B
   - General chat → Claude 3 Haiku
   - Complex analysis → Claude 3.5 Sonnet

2. **Reduce token usage**
   - Use concise system prompts
   - Limit `maxTokens` to what you actually need
   - Use `buildContextForBedrock()` (summaries) instead of `buildDetailedContextForBedrock()` (full descriptions)

3. **Cache responses** for identical prompts (Redis/in-memory)

4. **Use rate limiting** to prevent accidental cost spikes

5. **Monitor usage** via CloudWatch metrics

---

## 15. Security Best Practices

### 1. Never Hard-Code Credentials

```java
// ❌ NEVER DO THIS
BedrockRuntimeClient.builder()
    .credentialsProvider(StaticCredentialsProvider.create(
        AwsBasicCredentials.create("AKIAIOSFODNN7EXAMPLE", "secret")
    ))

// ✅ ALWAYS DO THIS
BedrockRuntimeClient.builder()
    .credentialsProvider(DefaultCredentialsProvider.create())
```

### 2. Use IAM Roles (Not Access Keys)

```
Development: Environment variables or AWS profiles
Production:  IAM Instance Profile (EC2) or Task Role (ECS)
```

### 3. Input Validation

```java
// ✅ Already implemented in PromptRequest.java
@NotBlank(message = "Prompt is required")
@Size(max = 100000, message = "Prompt too long")
private String prompt;
```

### 4. Don't Expose Internal Errors

```java
// ✅ Already implemented in GlobalExceptionHandler.java
// Returns generic message to client, logs details server-side
```

### 5. Enable HTTPS in Production

```yaml
# application-prod.yml
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${SSL_PASSWORD}
```

### 6. Restrict CORS Origins

```java
// ✅ In WebConfig.java — change for production
.allowedOrigins("https://your-app.com")
```

### 7. Protect Database Credentials

```yaml
# Use environment variables, not plain text in YAML
spring:
  datasource:
    password: ${DB_PASSWORD}  # Set via env var
```

---

## 16. Rate Limiting

### Current Implementation (Bucket4j)

```
Algorithm: Token Bucket
Capacity:  10 tokens (configurable via rate-limit.requests-per-minute)
Refill:    10 tokens per minute (greedy refill)

Behavior:
  Request 1-10:  ✅ Allowed (consumes 1 token each)
  Request 11:    ❌ HTTP 429 Too Many Requests
  After 6s:      ✅ 1 token refilled, next request allowed
```

### Production Rate Limiting Options

```
1. In-Memory (current)     → Good for single instance
2. Redis-backed Bucket4j   → Good for multiple instances
3. AWS API Gateway          → Best for public APIs
4. Spring Cloud Gateway     → Good for microservice mesh
```

---

## 17. Docker & Kubernetes Deployment

### Docker

```bash
# Build Spring Boot image
cd bedrock-api
docker build -t bedrock-api:1.0.0 .

# Build JS Client image
cd bedrock-js-client
docker build -t bedrock-js-client:1.0.0 .

# Run Spring Boot
docker run -d --name bedrock-api \
  -p 8080:8080 \
  -e AWS_ACCESS_KEY_ID=your-key \
  -e AWS_SECRET_ACCESS_KEY=your-secret \
  -e AWS_REGION=us-east-1 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/linkedin_naukr_jobs \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  bedrock-api:1.0.0

# Run JS Client
docker run -d --name bedrock-js-client \
  -p 3000:3000 \
  -e BEDROCK_API_URL=http://bedrock-api:8080 \
  --link bedrock-api \
  bedrock-js-client:1.0.0
```

### Kubernetes

```bash
# Create AWS credentials secret
kubectl create secret generic aws-credentials \
  --from-literal=access-key-id=YOUR_KEY \
  --from-literal=secret-access-key=YOUR_SECRET

# Deploy
kubectl apply -f bedrock-api/k8s/deployment.yaml
kubectl apply -f bedrock-api/k8s/service.yaml

# Verify
kubectl get pods -l app=bedrock-api
kubectl logs -f deployment/bedrock-api
```

---

## Quick Reference Card

| What | Command/URL |
|------|------------|
| Start Spring Boot | `mvn spring-boot:run` |
| Start JS Client | `npm start` |
| Health Check | `curl http://localhost:8080/api/v1/health` |
| Chat | `POST http://localhost:8080/api/v1/chat` |
| Analyze Jobs | `POST http://localhost:8080/api/v1/jobs/analyze` |
| List Jobs | `GET http://localhost:8080/api/v1/jobs` |
| Search Jobs | `GET http://localhost:8080/api/v1/jobs/search?keyword=Java` |
| List Models | `GET http://localhost:8080/api/v1/models` |
| Docker Build | `docker build -t bedrock-api:1.0.0 .` |
| K8s Deploy | `kubectl apply -f k8s/` |

---

### 🔑 Troubleshooting AWS Credentials in PowerShell

If you are using environment variables to authenticate with AWS Bedrock during development, you may need to verify or clear your credentials. 

#### Checking Active Credentials
To check if the environment variables are currently set in your active PowerShell window, you can "echo" them out by typing:

```powershell
$env:AWS_ACCESS_KEY_ID
$env:AWS_SECRET_ACCESS_KEY
$env:AWS_REGION
```
*(If it prints a blank line, the variable is not set in that specific terminal session).*

#### Clearing Credentials
If you want to remove them from your current PowerShell session (for example, if you pasted the wrong key by accident), you can simply set them to an empty string:

```powershell
$env:AWS_ACCESS_KEY_ID=""
$env:AWS_SECRET_ACCESS_KEY=""
$env:AWS_REGION=""
```

Alternatively, you can completely delete the variables using the `Remove-Item` command:

```powershell
Remove-Item Env:\AWS_ACCESS_KEY_ID
Remove-Item Env:\AWS_SECRET_ACCESS_KEY
Remove-Item Env:\AWS_REGION
```
