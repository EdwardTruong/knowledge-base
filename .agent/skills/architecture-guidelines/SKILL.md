---
name: architecture-guidelines
description: Enforce standard 3-tier architecture (Controller -> Service -> Repository) and avoid Hexagonal/Port-Adapter for repositories.
---

# Architecture Guidelines

When generating, modifying, or refactoring code in this project, **always follow the standard 3-tier architecture** for the persistence and service layers. Do **NOT** use the Hexagonal Architecture (Port and Adapter) pattern for repositories.

## Rules
1. **Controller Layer**:
   - Create a controller interface (e.g., `DocumentController`) to define the API contract and routing (e.g., Spring MVC annotations like `@GetMapping`, Swagger annotations).
   - Create a controller implementation class (e.g., `DocumentControllerImpl` implementing `DocumentController`) which handles the incoming requests.
   - `DocumentControllerImpl` must directly inject the `Service Interface`.

2. **Service Layer**:
   - Create a service interface (e.g., `DocumentService`).
   - Create a service implementation class (e.g., `DocumentServiceImpl` implementing `DocumentService`).
   - `DocumentServiceImpl` must directly inject the Spring Data `Repository` interface (e.g., `DocumentRepository`).

3. **Repository Layer**:
   - Repositories must directly extend Spring Data `JpaRepository` (or similar Spring Data interfaces).
   - Place them in the `infrastructure.persistence` (or `repository`) package.
   - Do **NOT** create a separate pure domain interface for the repository and an `Impl` class wrapping a `JpaRepository`.
   - Example: `public interface DocumentRepository extends JpaRepository<Document, UUID> { ... }`

4. **General Flow**:
   - `Controller Interface` -> `ControllerImpl` -> `Service Interface` -> `ServiceImpl` -> `Spring Data JpaRepository`.

ALWAYS apply these guidelines when requested to add a new entity, module, or when refactoring the persistence layer.
