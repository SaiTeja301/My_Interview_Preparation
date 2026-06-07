# 🚀 AWS Bedrock Project — Endpoints & Workflow

This document provides a quick overview of the system architecture, how the microservices communicate, and the available REST endpoints for the Bedrock AI integration.

---

## 🏗️ Project Workflow

The application follows the **Backend for Frontend (BFF)** and **MCP (Model Context Protocol)** patterns. We use a lightweight Node.js (Express) client to talk to the Spring Boot backend, which then queries a MySQL database and AWS Bedrock.

### System Architecture

```mermaid
graph TD
    User([👤 User / Postman]) --> |HTTP POST /analyze| JS_Client[🔵 JS Microservice (Port 3000)]
    JS_Client --> |Forwards Request| Spring_Boot[🟢 Spring Boot API (Port 8080)]
    
    subgraph Spring Boot Application
        Spring_Boot --> JobController[Controller Layer]
        JobController --> RateLimiter[Bucket4j Rate Limiting]
        RateLimiter --> JobService[Job Service]
    end
    
    JobService --> |1. Fetch Data| MySQL[(🐬 MySQL Database)]
    MySQL --> |Returns Job Rows| JobService
    
    JobService --> |2. Build MCP Context| ContextBuilder[Context Builder]
    ContextBuilder --> |Structured Text Context| BedrockService[Bedrock Service]
    
    BedrockService --> |3. Converse API (SigV4)| Bedrock[☁️ AWS Bedrock]
    
    subgraph AWS Cloud
        Bedrock --> Claude(Claude 3.5 Sonnet)
        Bedrock --> Titan(Titan Text)
    end
    
    Claude --> |4. AI Analysis| BedrockService
    BedrockService --> |Response DTO| JobController
    JobController --> |JSON| JS_Client
    JS_Client --> |Result| User
```

### The MCP (Model Context Protocol) Flow Step-by-Step

When a user asks: *"Which Java jobs have the least competition?"*

1. **Client Request**: The request hits the `POST /api/v1/jobs/analyze` endpoint.
2. **Data Retrieval**: Spring Boot queries the `linkedin_naukr_jobs` MySQL database for jobs containing "Java".
3. **Context Injection**: The database rows are transformed into a dense, token-optimised string.
4. **Prompt Construction**: Spring Boot combines the System Prompt + Database Context + User Question.
5. **Bedrock Inference**: The combined prompt is sent to AWS Bedrock (e.g., Claude 3.5 Sonnet) via the `Converse` API.
6. **Response Generation**: Claude analyses the injected context and answers the user's specific question based *only* on the provided data.

---

## 📡 Available Endpoints

The project exposes two sets of endpoints: 
1. The **Spring Boot API** (Backend — port `8080`)
2. The **Node.js Client** (Proxy — port `3000`)

*Both expose the same paths. You can query either `http://localhost:3000` or `http://localhost:8080`.*

### 1. 🤖 AI & Bedrock Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/v1/chat` | Send a direct text prompt to an AWS Bedrock model. |
| **POST** | `/api/v1/jobs/analyze` | Ask questions about job data using AI analysis (MCP). |
| **GET** | `/api/v1/models` | List all available Bedrock models in your AWS region. |

#### Example: Chat Request
```json
// POST /api/v1/chat
{
  "prompt": "Explain AWS Bedrock in simple terms.",
  "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
  "maxTokens": 500,
  "temperature": 0.5
}
```

#### Example: Job Analysis Request
```json
// POST /api/v1/jobs/analyze
{
  "question": "Which jobs should I apply to if I only know Java?",
  "keyword": "Java",
  "includeFullDescription": false
}
```

---

### 2. 📊 Database CRUD Endpoints

These endpoints interact directly with the MySQL database without calling Bedrock AI.

| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/api/v1/jobs` | Retrieve all job listings from the database. |
| **GET** | `/api/v1/jobs/{id}` | Retrieve a specific job by its ID. |
| **GET** | `/api/v1/jobs/search?keyword=...` | Search for jobs by a keyword (e.g., `?keyword=React`). |
| **GET** | `/api/v1/jobs/stats` | Get basic statistics (total jobs, unapplied jobs, aggregated summaries). |

---

### 3. ⚙️ System Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/api/v1/health` | Custom health check endpoint to verify Spring Boot & AWS connection. |
| **GET** | `/actuator/health` | Spring Boot Actuator health metric. |
| **GET** | `/` *(JS Client only)* | Shows the available JS client endpoints and status. |
| **GET** | `/health` *(JS Client only)*| Checks the health of both the JS Client and Spring Boot API. |

---

## 🛠️ How to Test the Flow

1. **Start the Database**: Ensure MySQL is running on port `3306` with the `linkedin_naukr_jobs` database and the `jobs` table populated.
2. **Start Spring Boot**:
   ```bash
   cd bedrock-api
   mvn spring-boot:run
   ```
3. **Start the JS Client**:
   ```bash
   cd bedrock-js-client
   npm install
   npm start
   ```
4. **Trigger an AI Analysis**:
   ```bash
   curl -X POST http://localhost:3000/jobs/analyze \
     -H "Content-Type: application/json" \
     -d '{"question": "Give me a summary of jobs located in Bengaluru."}'
   ```
