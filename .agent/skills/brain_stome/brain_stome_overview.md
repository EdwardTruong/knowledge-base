# 🚀 KB-AI SYSTEM — EXECUTION BLUEPRINT

---

# 1. 🧭 SYSTEM VISION (RECONFIRM)

## Core Idea

> Build a **Personal AI Brain** that:

* Understands your knowledge (coding, trading, ideas)
* Helps you think, plan, and act
* Can interact via text + voice + IoT

---

# 2. 🧱 SYSTEM BREAKDOWN (WHAT YOU MUST BUILD)

## 2.1 MUST HAVE (MVP)

### 🔹 Knowledge Base (RAG)

* Upload document
* Chunking
* Embedding
* Vector search
* AI answer

---

### 🔹 AI Query Engine

* Prompt builder
* Context injection
* Response formatter

---

### 🔹 Basic API

* `/kb/upload`
* `/kb/query`

---

## 2.2 SHOULD HAVE

### 🔹 Planning System

* Task CRUD
* Timeline
* Priority

---

### 🔹 Voice Assistant

* Speech-to-text
* Text-to-speech
* Realtime response

---

## 2.3 FUTURE

* IoT control
* Smart recommendation
* Auto planning AI

---

# 3. 🧠 TECH DECISIONS (LOCK THESE)

## Backend

* Java + Spring Boot
* Spring AI

## AI

* Ollama (local first)
* Model:

  * llama3 (general)
  * codellama (coding)

## Vector DB

* Qdrant

## DB

* PostgreSQL

## Voice

* FastAPI + Whisper + Edge TTS

---

# 4. 📂 PROJECT STRUCTURE (FINAL)

```id="final-structure"
kb-ai/
├── backend/
│   ├── gateway/
│   ├── knowledge-service/
│   ├── planning-service/
│
├── python-ai/
│   ├── voice-service/
│   ├── tts/
│   ├── stt/
│
├── infrastructure/
│   ├── docker/
│   ├── k8s/ (future)
│
├── docs/
```

---

# 5. 🔄 IMPLEMENTATION PLAN (STEP-BY-STEP)

---

## PHASE 1 — SETUP CORE (2–3 days)

### ✅ Task 1: Setup Project

* Create Spring Boot project
* Add Spring AI
* Setup Docker

Checklist:

* [ ] Spring Boot chạy
* [ ] Docker compose chạy
* [ ] Ollama chạy local

---

## PHASE 2 — RAG CORE (CRITICAL)

### ✅ Task 2: Document Ingestion

You must implement:

```id="task-ingestion"
Input → file/text
→ split text (chunking)
→ call embedding API
→ save vector DB
```

Checklist:

* [ ] Chunking logic (500–1000 tokens)
* [ ] Metadata (source, tag, time)
* [ ] Save to Qdrant

---

### ✅ Task 3: Query Engine

```id="task-query"
Input → question
→ embedding
→ vector search (top K)
→ build prompt
→ call LLM
→ return answer
```

Checklist:

* [ ] Top K search (3–5)
* [ ] Prompt template
* [ ] Response format JSON

---

## PHASE 3 — PROMPT ENGINEERING

### ✅ Task 4: Prompt Template

```text id="prompt-template"
You are a personal AI assistant.

Use ONLY the provided context.

If not found → say "I don't know".

Context:
{context}

Question:
{question}
```

Checklist:

* [ ] No hallucination
* [ ] Clear answer
* [ ] Structured output

---

## PHASE 4 — API LAYER

### ✅ Task 5: Build API

Endpoints:

* POST /kb/upload
* POST /kb/query
* GET /kb/docs

Checklist:

* [ ] Validate input
* [ ] Handle error
* [ ] Logging

---

## PHASE 5 — VOICE SYSTEM

### ✅ Task 6: Speech-to-Text

* Use Whisper

Checklist:

* [ ] Audio → text
* [ ] Vietnamese support

---

### ✅ Task 7: Text-to-Speech

* Edge TTS (FREE)

Checklist:

* [ ] Vietnamese female voice
* [ ] Save mp3
* [ ] Streaming response

---

## PHASE 6 — INTEGRATION

### ✅ Task 8: Connect Java ↔ Python

* REST call

Checklist:

* [ ] Java call FastAPI
* [ ] Return audio
* [ ] Handle timeout

---

## PHASE 7 — PLANNING SYSTEM

### ✅ Task 9: Task Management

* Create task
* Update status
* Deadline

Checklist:

* [ ] CRUD API
* [ ] DB schema

---

# 6. 🧩 DESIGN PATTERNS YOU MUST USE

## Backend

* Controller → Service → Repository
* Strategy → AI model selection
* Factory → Prompt builder
* Adapter → external AI

---

## AI Layer

* RAG pattern
* Context Injection
* Prompt Template

---

# 7. 📊 DATA MODEL

## Document

```json id="doc-model"
{
  "id": "uuid",
  "title": "...",
  "content": "...",
  "tags": ["trading"],
  "created_at": "..."
}
```

---

## Vector

```json id="vector-model"
{
  "id": "...",
  "embedding": [...],
  "metadata": {
    "doc_id": "...",
    "chunk": "..."
  }
}
```

---

## Task

```json id="task-model"
{
  "id": "...",
  "title": "...",
  "status": "TODO",
  "priority": "HIGH",
  "deadline": "..."
}
```

---

# 8. ⚠️ COMMON FAILURES (AVOID)

❌ Chunk quá lớn → AI ngu
❌ Không filter context → hallucination
❌ Prompt yếu → trả lời sai
❌ Không log → debug chết

---

# 9. 📈 OPTIMIZATION (LATER)

* Hybrid search (keyword + vector)
* Caching
* Multi-model routing
* Fine-tune embedding

---

# 10. 🎯 FINAL CHECKLIST

## MVP READY WHEN:

* [ ] Upload document works
* [ ] Query trả lời đúng context
* [ ] AI không hallucinate
* [ ] API stable
* [ ] Docker chạy full system

---

# 🔥 FINAL MESSAGE

> Don’t try to build everything.
> Build **RAG thật tốt trước** → everything else will be easy.

---
