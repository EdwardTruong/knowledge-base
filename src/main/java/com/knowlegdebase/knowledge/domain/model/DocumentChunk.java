package com.knowlegdebase.knowledge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.knowlegdebase.shared.domain.BaseEntity;

import java.util.UUID;

/**
 * Represents a smaller, subdivided segment of a {@link Document}.
 * 
 * In the context of a Knowledge Base and RAG (Retrieval-Augmented Generation) system,
 * large documents are split into chunks. These chunks are then converted into vector 
 * embeddings and stored in a vector database (like Qdrant) to enable efficient 
 * semantic search.
 */
@Entity
@Table(name = "document_chunks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentChunk extends BaseEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "chunk_text", columnDefinition = "TEXT", nullable = false)
    private String chunkText;

    @Column(name = "chunk_index", nullable = false)
    private int chunkIndex;

    @Column(name = "token_size", nullable = false)
    private int tokenSize;

    @Column(name = "is_embedded", nullable = false)
    private boolean embedded;

    public DocumentChunk(Document document, String chunkText, int chunkIndex, int tokenSize) {
        this.document = document;
        this.chunkText = chunkText;
        this.chunkIndex = chunkIndex;
        this.tokenSize = tokenSize;
        this.embedded = false;
    }

    public void markAsEmbedded() {
        this.embedded = true;
    }
}
