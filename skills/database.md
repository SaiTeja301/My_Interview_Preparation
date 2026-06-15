# Database Management Skills

This document details the database, caching, and storage skills demonstrated by the projects in this repository.

---

## 🏆 Summary of Database Skills

| Technology / Concept | Proficiency | Confidence | Primary Evidence |
| :--- | :--- | :--- | :--- |
| **SQL Server & MySQL** | Advanced | 95% | Database design, schema modeling, query optimization, join tuning |
| **PL/SQL & Transactions**| Advanced | 93% | Stored procedures, dynamic queries, `@Transactional` configuration |
| **MongoDB** | Intermediate | 88% | Document data structures, NoSQL collections, CRUD, nested documents |
| **Caching Strategies (Redis)** | Intermediate | 90% | Redis key-value cache, session clustering, TTL expiration config |
| **Vector Databases** | Intermediate | 85% | Vector search integration for RAG, embedding vectors storage |

---

## 🔍 Detailed Skills Breakdown

### 1. SQL Server & MySQL (Relational Databases)
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 95%
*   **Evidence**:
    *   **Schema Design**: Designing normalized schemas for the `Customer Loan Management System` (Customer, Loan, Address, LoanType) and the `IKEA Retail System` (Order, Product, Inventory).
    *   **Query Optimization**: Tuning SQL queries using indexes (clustered, non-clustered, composite), avoiding full table scans, analyzing execution plans, and optimizing join paths.
    *   **Data Consistency**: Strong transactional control and foreign key constraints across relational schemas.
*   **File References**:
    *   [SQL_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/SQL_Analysis.md)
    *   [Project_Deep_Analysis.md (Section 4 - Database Schema)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/Project_Deep_Analysis.md#L60-L86)
    *   [ICA_Analysis.txt (Section 8 - Production Challenges)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L170-L182)

### 2. PL/SQL & Transaction Management
*   **Proficiency Level**: Advanced
*   **Confidence Score**: 93%
*   **Evidence**:
    *   **PL/SQL**: Writing stored procedures, functions, triggers, and cursor-based data processing logic.
    *   **Spring Transaction Management**: Fine-grained transaction boundaries set using `@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)`.
    *   **Concurrency Controls**: Managing optimistic locking (`@Version` attribute) and pessimistic locking (`LockModeType.PESSIMISTIC_WRITE`) to handle race conditions in inventory and financial ledger calculations.
*   **File References**:
    *   [Resume.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Resume.txt)
    *   [Hibernate_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/Hibernate_Analysis.md)

### 3. MongoDB (NoSQL)
*   **Proficiency Level**: Intermediate
*   **Confidence Score**: 88%
*   **Evidence**:
    *   **Document Modeling**: Structuring semi-structured data with flexible document modeling schemas (collections, nested BSON documents).
    *   **Data Access Integration**: Integrating Spring Boot with MongoDB using `MongoRepository` and writing custom criteria queries via `MongoTemplate`.
    *   **Operations**: Indexing fields, executing aggregation pipelines, and structuring CRUD transactions.
*   **File References**:
    *   [MongoDB_Analysis.md](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Java_jdbc_hibernate_SpringBoot/analysis/MongoDB_Analysis.md)
    *   [Resume.txt](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Resume.txt)

### 4. Caching Strategies (Redis)
*   **Proficiency Level**: Intermediate
*   **Confidence Score**: 90%
*   **Evidence**:
    *   **Response Caching**: Implementing Redis as a fast key-value store cache layer in microservices (e.g. caching similar OpenAI queries with a 5-minute TTL to reduce API expenses).
    *   **Session Management**: Application of stateless distributed session structures mapped onto a central Redis cluster.
    *   **Search Caching**: Caching frequent SOPA Search results (2-minute TTL) to reduce downstream REST call loads.
*   **File References**:
    *   [National_Analysis.txt (Section 2 - SOPA Search Aggregator)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/National_Analysis.txt#L44-L91)
    *   [ICA_Analysis.txt (Section 5 - OpenAI LLM Integration Flow)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L101-L126)

### 5. Vector Databases
*   **Proficiency Level**: Intermediate
*   **Confidence Score**: 85%
*   **Evidence**:
    *   **RAG Architecture**: Integration of a vector database (pgvector, Pinecone, or Weaviate) to store product text embedding vectors.
    *   **Vector Querying**: Executing cosine similarity search queries in vector space to retrieve the top 5 matching product contexts for grounding OpenAI prompt templates.
*   **File References**:
    *   [ICA_Analysis.txt (Section 6 - RAG Implementation Architecture)](file:///E:/Teja_Interview_preparation/My_Interview_Preparation/Resume/Company_Projects/ICA_Analysis.txt#L127-L150)

---
*Last updated: 2026-06-16 | Maintained by: Living Technical Skills Analyzer*
