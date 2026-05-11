# 🧠 KB-AI — Project Structure Blueprint (Spring Modulith)

> This document defines the project structure, architectural boundaries, and AI code generation rules for the KB-AI system, reflecting its implementation as a Spring Boot Modulith.

---

# 🎯 Purpose

This document exists to:
* standardize the Modulith project structure,
* guide AI-assisted code generation (CQRS, Layering),
* maintain architectural integrity using Spring Modulith,
* ensure clear boundaries between business modules.

---

# 🏗️ PROJECT ROOT STRUCTURE

The project is a single Spring Boot application located in the `core-api` directory.

```text
knowledge-base/
├── core-api/               # Main Spring Boot Application
│   ├── src/main/java/
│   │   └── com/knowlegdebase/
│   │       ├── knowledge/  # Knowledge Module (RAG, Vector Search)
│   │       ├── planning/   # Planning Module (Tasks, Execution)
│   │       └── shared/     # Shared Kernel (CQRS, Base Entities)
│   ├── pom.xml
│   └── ...
├── .agent/                 # Agent instructions and blueprints
└── ...
```

---

# 📦 ARCHITECTURAL STYLE

The system follows **Spring Modulith** with **CQRS** and a **Rich Domain Model**.

| Pattern | Responsibility |
| -------------- | -------------------------------------- |
| **Spring Modulith** | Logical modularization within a single monolith. |
| **CQRS** | Separation of Read (Query) and Write (Command) operations. |
| **Rich Domain Model** | Business logic encapsulated within Domain Entities and Services. |
| **4-Tier Layering** | Standardized internal structure for each module. |

---

# 🧱 MODULE STRUCTURE

Each business module (e.g., `knowledge`, `planning`) follows a strict 4-tier internal layering:

### 1. 🌐 Web Layer (`web/`)
* REST Controllers and API endpoints.
* Handles HTTP requests/responses and DTO mapping.

### 2. ⚡ Application Layer (`application/`)
* **Commands & Queries**: Data structures representing actions or requests.
* **Handlers**: Logic to process Commands and Queries.
* **Services**: Orchestration logic (Application Services).

### 3. 🛡️ Domain Layer (`domain/`)
* **Entities**: Core business objects (Rich Domain Model).
* **Value Objects**: Immutable objects representing domain concepts.
* **Repository Interfaces**: Abstractions for data access.
* **Domain Services**: Logic that doesn't fit naturally in an Entity.

### 4. ⚙️ Infrastructure Layer (`infrastructure/`)
* **Persistence**: JPA/Hibernate implementations of Repository interfaces.
* **External Clients**: LLM integrations (Spring AI), Vector DB clients (ChromaDB).
* **Configurations**: Module-specific Spring configurations.

---

# 📚 MODULE DETAILS

## 🧠 Knowledge Module (`knowledge/`)
Handles RAG (Retrieval Augmented Generation), document chunking, embeddings, and vector search.
* **Domain**: `Document`, `DocumentChunk`, `DocumentSource`.
* **Application**: `UploadDocumentCommand`, `SearchKnowledgeQuery`.
* **Infrastructure**: `ChromaVectorStore`, `DocumentJpaRepository`.

## 📝 Planning Module (`planning/`)
Handles task management and execution flows.
* **Domain**: `Task`, `ExecutionPlan`, `Goal`.
* **Application**: `CreateTaskCommand`, `ExecutePlanCommandHandler`.

## 🤝 Shared Module (`shared/`)
Contains cross-cutting concerns and the Shared Kernel.
* **CQRS**: `Command`, `Query`, `CommandGateway`, `QueryGateway`.
* **Domain**: `BaseEntity`, `AggregateRoot`.
* **Utils**: Common helper classes.

---

# ⚙️ AI CODE GENERATION RULES

## IMPORTANT
AI-generated code MUST:
1.  **Respect Module Boundaries**: Do not access internal classes of another module. Use `shared` or public APIs.
2.  **Follow CQRS Pattern**: Every state-changing operation must be a `Command`. Every retrieval must be a `Query`.
3.  **Use DTOs**: Never expose Domain Entities directly in the Web layer.
4.  **Rich Entities**: Prefer putting logic in Entities over Services when it belongs to the domain object.
5.  **Package Naming**: Always use `com.knowlegdebase` prefix.

---

# 🚀 FUTURE VISION

While currently a single Modulith, the structure is designed to be easily split into microservices if needed. Frontend (PWA) and specialized Python AI services (Voice/TTS) will be added as sibling directories in the future.

---

# 🧠 FINAL PHILOSOPHY

> "Consistency over cleverness."
> "Structure first. Logic second. Optimization later."
