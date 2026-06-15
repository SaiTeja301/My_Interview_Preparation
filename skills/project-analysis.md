# Project Analysis & Learning Recommendations

This document summarizes the enterprise projects analyzed in this repository and outlines strategic learning recommendations to bridge the gap toward Principal and Staff Engineer roles.

---

## 📂 Summary of Analyzed Projects

The codebase and notes contain deep analyses of four distinct application projects, representing diverse architectural designs and stack configurations:

### 1. Nationwide Mutual Insurance Platform (OdaAdmin Service & UI)
*   **Architecture**: Modular Monolith transitioning to Microservices
*   **Backend Stack**: Java 21 + Spring Boot 3.5.7 + Spring Data JPA + Spring Security (JWT)
*   **Frontend Stack**: Angular 19 (Standalone Components) + RxJS + Bootstrap
*   **Key Features**: Multi-layer structure (Controller -> Service -> Repository -> Entity -> DTO), Global `@RestControllerAdvice` error mapper, paginated endpoints via Spring Data Pageable, custom JWT auth filters, and CORS management.
*   **Deployment**: CI/CD via Jenkins & Harness, containerized runtimes on Rancher (Kubernetes), and code quality checks using SonarQube gates.
*   **Key Files**:
    *   [OdaAdmin Service Analysis](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/CWP_CTS/Spring_boot_project_analysis/analysis.md)
    *   [OdaAdmin UI Analysis](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/CWP_CTS/AgularProject/analysis.md)

### 2. IKEA Internal Retail Systems (ICA)
*   **Architecture**: Microservices with Event-Driven SAGA Pattern
*   **Backend Stack**: Java 17 + Spring Boot + Spring Cloud Gateway + Kafka + SQL Server
*   **Frontend Stack**: Angular + RxJS (Typeahead switchMap + BehaviorSubject State)
*   **AI Integration**: OpenAI GPT-4 API via secure RAG (Retrieval-Augmented Generation) pipeline using Vector DB (pgvector/Pinecone) for similarity searches.
*   **Services**: API Gateway, Order Service, Product Service, Payment Service (Stripe/Razorpay), LLM Service, and Notification Service.
*   **Key Files**:
    *   [ICA_Analysis.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt)

### 3. Customer Loan Management System
*   **Architecture**: Classic Layered Monolith
*   **Backend Stack**: Spring Boot + Spring Data JPA + MySQL
*   **Frontend Stack**: Angular (CustomerApp-UI)
*   **Key Features**: Customer CRUD endpoints, loan application workflow calculating EMIs, custom exception handling (`CustomerNotFoundException`), Pageable pagination, and role-based endpoints (ADMIN vs CUSTOMER) secured by JWT.
*   **Key Files**:
    *   [Project_Deep_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/Project_Deep_Analysis.md)

### 4. AWS Bedrock Project
*   **Architecture**: Backend-For-Frontend (BFF) Microservices
*   **Backend Stack**: Spring Boot 3.2.5 (Java 17) + AWS SDK v2 (`bedrockruntime`) + Bucket4j + MySQL
*   **Frontend/BFF Stack**: Node.js + Express + Axios (Exponential backoff wrapper)
*   **Key Features**: Server-side rate limiting using token-bucket algorithm (`Bucket4j`), Converse API mapping to Amazon Bedrock Claude AI models, STS credential validation, multi-stage Docker builds, and non-root runtime environments.
*   **Key Files**:
    *   [pom.xml (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/pom.xml)
    *   [Dockerfile (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/Dockerfile)
    *   [bedrockClient.js](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-js-client/src/services/bedrockClient.js)

---

## 🛠️ Key Technical Challenges Solved

The following issues were successfully addressed in the analyzed projects:

1.  **Kafka Consumer Lag (Black Friday spikes)**: Resolved by increasing partition counts and horizontally scaling consumer pod instances to match partition counts.
2.  **Payment Gateway Timeouts**: Remediated by implementing Resilience4j Circuit Breakers and retries with backoff strategies.
3.  **LLM API Rate Limits & Cost**: Solved by implementing Redis response caching for similar queries (5-minute TTL) and request queue queues.
4.  **Database Connection Pool Exhaustion**: Solved by tuning HikariCP thread limits (`maximumPoolSize=20`) and deploying SQL Server read replicas for read-heavy product queries.
5.  **JVM OOM container crashes**: Remediated by configuring container-aware JVM flags (`-XX:+UseContainerSupport`, `-XX:MaxRAMPercentage=75.0`).
6.  **Prompt Injection Vulnerabilities**: Blocked by applying strict input sanitization and structuring strong system prompts in the LLM service.

---

## 📈 Learning Recommendations for Missing Enterprise Skills

To transition from a Senior Full-Stack Developer to a **Principal/Staff Engineer** or **Software Architect**, Sai Teja should focus on the following core domains:

### 1. React & Modern Frontend Frameworks
*   **Rationale**: While Angular is highly valuable in enterprise environments, React is widely used across start-ups and major cloud-native companies. Mastery of React allows a developer to serve as a versatile frontend architect.
*   **Recommendation**:
    *   Learn React hooks (`useState`, `useEffect`, `useContext`, `useMemo`, `useCallback`).
    *   Study state management libraries (Redux Toolkit, Zustand, or Jotai).
    *   Build a project using Next.js to understand server-side rendering (SSR), static site generation (SSG), and React Server Components (RSC).

### 2. Reactive Programming & Spring WebFlux
*   **Rationale**: The current services rely on blocking servlet APIs (Spring MVC, Tomcat, sync JDBC). To design high-concurrency systems, a developer must understand non-blocking reactive stacks.
*   **Recommendation**:
    *   Study Project Reactor (Mono, Flux, operators) and RxJS concepts on the backend.
    *   Learn Spring WebFlux and construct a fully reactive microservice.
    *   Integrate R2DBC (Reactive Relational Database Connectivity) to establish reactive database access pipelines, eliminating blocking HikariCP thread bottlenecks.

### 3. Advanced Cloud Infrastructure (Infrastructure as Code)
*   **Rationale**: Managing deployments via Rancher dashboard or running manual Docker builds is insufficient at the Principal level. Architects must design infrastructure programmatically.
*   **Recommendation**:
    *   Master **Terraform**: Write modular, state-managed configurations to provision multi-region AWS environments (VPCs, ECS clusters, RDS instances, IAM roles).
    *   Learn **Kubernetes Manifests & Helm**: Move beyond dashboards. Write raw K8s deployments, services, ingress controllers, configmaps, and package them as reusable Helm charts.

### 4. Advanced Security Protocols (OAuth2 & OIDC)
*   **Rationale**: Basic JWT filters are suitable for internal microservices, but enterprise architectures demand federated identity managers (Okta, Keycloak, Auth0) running OAuth2 / OpenID Connect (OIDC) protocols.
*   **Recommendation**:
    *   Understand the OAuth2 flow types (Authorization Code Grant with PKCE, Client Credentials).
    *   Configure Spring Security as an **OAuth2 Resource Server** to validate tokens against identity providers dynamically.
    *   Implement Single Sign-On (SSO) gateways across frontend applications.

### 5. Advanced AI Engineering (Agentic Frameworks & Vector Search)
*   **Rationale**: Traditional RAG pipelines are only the first step. Next-generation systems leverage autonomous multi-agent frameworks.
*   **Recommendation**:
    *   Study **Spring AI**: Learn Spring's new starter dependencies for LLMs, vector database abstraction layers, and chat clients.
    *   Explore Agentic Frameworks: Research LangChain, LangGraph, or Semantic Kernel to design workflows where LLMs plan actions, call APIs, and self-correct.

---
*Last updated: 2026-06-16 | Maintained by: Living Technical Skills Analyzer*
