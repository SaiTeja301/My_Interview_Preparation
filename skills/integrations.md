# System Integrations Skills

This document details the system-to-system integrations, message brokers, external APIs, and AI integrations demonstrated by the projects in this repository.

---

## 🏆 Summary of Integrations Skills

| Integration Target | Proficiency | Confidence | Primary Evidence |
| :--- | :--- | :--- | :--- |
| **Apache Kafka**     | Advanced | 95% | Topics, partitioning, consumer groups, offsets, Poison/DLT, Burrows monitoring |
| **OpenAI API & RAG**  | Advanced | 92% | GPT-4 Chat API, Embeddings API, similarity search vector DB, prompt injection defense |
| **AWS Bedrock Runtime**| Advanced | 90% | Bedrock runtime client, AWS SDK v2, list models, STS validation, credentials |
| **Payment Gateways**  | Advanced | 90% | Stripe/Razorpay WebClient calls, idempotency keys, Circuit Breaker retries |
| **RabbitMQ**         | Intermediate | 88% | Exchanges (Direct/Fanout/Topic), queues, bindings, routing keys, listener mapping |

---

## 🔍 Detailed Skills Breakdown

### 1. Apache Kafka Message Broker
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 95%
*   **Evidence**:
    *   **Topic Configuration**: Designing event-driven streaming structures with designated partitions (e.g. `order-events` with 6 partitions) to maximize consumer throughput.
    *   **Consumer Groups**: Scaling data-consumption pipelines across multiple consumer instances, managing consumer heartbeats, and rebalancing parameters.
    *   **Poison Message Remediation**: Resilient error routing using Spring Kafka `@RetryableTopic` and `@DltHandler` to isolate failures to a Dead Letter Topic (DLT).
    *   **Message Ordering**: Guaranteeing payload sequencing within partitions by assigning domain entities (e.g. `orderId`) as message partition keys.
*   **File References**:
    *   [Kafka_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/Kafka_Analysis.md)
    *   [ICA_Analysis.txt (Section 3 & Section 6)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt)
    *   [National_Analysis.txt (Section 4 - Kafka Loose Coupling Architecture)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/National_Analysis.txt#L122-L142)

### 2. OpenAI API & RAG Architecture
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 92%
*   **Evidence**:
    *   **RAG Pipeline**: Resolving LLM hallucination issues by chunking product catalog data, generating embedding vectors via OpenAI Embeddings API, and storing vectors in pgvector/Pinecone.
    *   **Similarity Search**: Implementing similarity search at query time to retrieve context snippets, injecting them into prompt templates, and generating responses via GPT-4.
    *   **Prompt Injection Protection**: strip-sanitizing special characters, setting system boundaries, and validating response schemas.
*   **File References**:
    *   [ICA_Analysis.txt (Section 5 & Section 6)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L101-L150)

### 3. AWS Bedrock Runtime Integration
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 90%
*   **Evidence**:
    *   **SDK Runtime Client**: Utilizing the AWS SDK v2 `bedrockruntime` client to access foundational models (e.g., Anthropic Claude).
    *   **Credential Resolution**: Fetching credentials dynamically using `DefaultCredentialsProvider` alongside `sts` client token requests.
    *   **Express BFF Routing**: Establishing a BFF layer to proxy user queries to the Spring Boot REST API Bedrock wrapper rather than directly exposing AWS credentials to the web interface.
*   **File References**:
    *   [pom.xml (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/pom.xml)
    *   [bedrockClient.js](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-js-client/src/services/bedrockClient.js)

### 4. Payment Gateway Integration (Stripe & Razorpay)
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 90%
*   **Evidence**:
    *   **REST Calls**: Executing payment validation requests to Stripe/Razorpay endpoints using Spring Boot `WebClient`.
    *   **Idempotent Transactions**: Appending an idempotency key (e.g. `orderId`) to requests to prevent duplicate charges.
    *   **Fault Isolation**: Protecting payment integrations with circuit breakers (Resilience4j) to fallback to a retry queue if gateway timeouts occur.
*   **File References**:
    *   [ICA_Analysis.txt (Section 7 - Payment Service Flow)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L152-L169)

### 5. RabbitMQ Message Broker
*   **Proficiency Level**: Intermediate
*   **Confidence Score**: 88%
*   **Evidence**:
    *   **Routing Architecture**: Implementing publisher-subscriber patterns using exchanges (Direct, Fanout, and Topic) bound to queues with routing keys.
    *   **Listener Configuration**: Implementing message listeners in Spring Boot using `@RabbitListener`.
*   **File References**:
    *   [Microservices with RabbitMQ.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/Microservices%20with%20RabbitMQ.txt)
    *   [RabbitMQ_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/RabbitMQ_Analysis.md)

---
*Last updated: 2026-06-16 | Maintained by: Living Technical Skills Analyzer*
