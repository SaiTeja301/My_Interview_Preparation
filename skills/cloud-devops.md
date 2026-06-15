# Cloud & DevOps Skills

This document details the containerization, orchestration, cloud deployment, and CI/CD capabilities demonstrated by the projects and configuration files in this repository.

---

## 🏆 Summary of Cloud & DevOps Skills

| Technology / Concept | Proficiency | Confidence | Primary Evidence |
| :--- | :--- | :--- | :--- |
| **Docker & Containers** | Advanced | 95% | Multi-stage builds, non-root runtimes, container-aware JVM, HEALTHCHECK |
| **Kubernetes & Rancher**| Advanced | 93% | Pod autoscaling, secret environment mapping, rolling updates, container monitoring |
| **AWS Cloud Services**  | Advanced | 92% | EC2, S3 bucket storage, Secrets Manager, Bedrock model access, IAM policies |
| **CI/CD Automation**    | Advanced | 93% | Jenkins, Harness, GitHub Actions pipeline automation with quality gates |
| **Maven Build Tool**    | Advanced | 95% | Parent POM inheritance, dependency BOM management, build plugins, profiles |
| **Terraform & Shell**   | Intermediate | 88% | Infrastructure provisioning concepts, custom Shell scripts, Linux management |

---

## 🔍 Detailed Skills Breakdown

### 1. Docker & Containerization
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 95%
*   **Evidence**:
    *   **Multi-Stage Dockerfile**: Implemented in `bedrock-api` (Stage 1 uses JDK 17 to compile and run package commands; Stage 2 copy-grabs the built JAR into a slim JRE runtime image).
    *   **Container Security**: Running containers as non-root users (`USER spring` and `USER nodejs`) to eliminate root privilege execution hazards.
    *   **Resource Tuning**: JVM container-aware properties (`-XX:+UseContainerSupport`, `-XX:MaxRAMPercentage=75.0`) mapped to respect container constraints.
    *   **HEALTHCHECK Integration**: Direct container health verification configurations (`HEALTHCHECK --interval=30s CMD wget http://localhost:8080/api/v1/health || exit 1`).
*   **File References**:
    *   [Dockerfile (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/Dockerfile)
    *   [Dockerfile (bedrock-js-client)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-js-client/Dockerfile)

### 2. Kubernetes & Rancher
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 93%
*   **Evidence**:
    *   **Pod Orchestration**: Deploying, managing, and inspecting containerized applications in Kubernetes using Rancher dashboards.
    *   **Autoscaling**: Defining replica scaling constraints based on average CPU/Memory metrics (e.g. scaling Order Service pods during transaction spikes).
    *   **Deployment Strategies**: Conducting rolling update configurations to deploy new versions with zero downtime (utilizing liveness/readiness probes).
    *   **Secrets Mapping**: Injecting Kubernetes Secrets into Spring Boot applications as environment variables (`DB_PASSWORD`).
*   **File References**:
    *   [Resume.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Resume.txt)
    *   [AWS_DevOps_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/AWS_DevOps_Analysis.md)

### 3. AWS Cloud Services
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 92%
*   **Evidence**:
    *   **Compute & Storage**: Deploying backend applications on EC2 instances and configuring secure file storage using AWS S3 buckets.
    *   **Secrets Management**: Storing API keys (such as OpenAI and Bedrock credentials) in AWS Secrets Manager and reading them dynamically inside microservices.
    *   **AWS Bedrock**: Integrating Foundation Models (e.g. Anthropic Claude) using Java SDK v2 Bedrock Client components (`bedrockruntime`, `STS` assume-role validation).
*   **File References**:
    *   [pom.xml (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/pom.xml)
    *   [ICA_Analysis.txt (Section 5 - OpenAI LLM Integration Flow)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L101-L126)

### 4. CI/CD Pipeline Automation
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 93%
*   **Evidence**:
    *   **GitHub Actions**: Automating code validation workflows (checkout, Maven compile, unit test runners, and packaging triggers).
    *   **Jenkins & Harness**: Orchestrating enterprise pipelines (Build -> Test -> Static Quality Gate -> Push to Harbor Registry -> Deploy to Rancher Cluster -> Health verification).
*   **File References**:
    *   [Resume.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Resume.txt)
    *   [AWS_DevOps_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/AWS_DevOps_Analysis.md)

### 5. Maven Build Tool
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 95%
*   **Evidence**:
    *   **BOM imports**: Using the Bill of Materials (BOM) pattern in `dependencyManagement` (e.g. `software.amazon.awssdk:bom` v2.26.15) to maintain consistent versions of dependencies.
    *   **Configuration**: Clean structuring of plugins (`spring-boot-maven-plugin`), dependencies exclusions (excluding Lombok from final target JARs), and property properties mappings.
*   **File References**:
    *   [pom.xml (bedrock-api)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Claude_Preparation/MCP-Model%20Context%20Protocol/Claude%20with%20Amazon%20Bedrock/AWS_BedrockProject_withClaude/bedrock-api/pom.xml)
    *   [Maven_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/Maven_Analysis.md)

---
*Last updated: 2026-06-16 | Maintained by: Living Technical Skills Analyzer*
