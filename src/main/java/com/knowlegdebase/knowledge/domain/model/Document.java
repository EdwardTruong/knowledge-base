package com.knowlegdebase.knowledge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.knowlegdebase.shared.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The core domain entity representing a document in the Knowledge Base.
 * 
 * A Document serves as the aggregate root for knowledge content. It holds the 
 * original text content, metadata (like title, type, and source), and tracks 
 * its progression through the processing pipeline via {@link DocumentStatus}.
 * 
 * A large Document is typically broken down into multiple {@link DocumentChunk}s 
 * which are subsequently embedded for vector search.
 */
@Entity
@Table(name = "documents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private DocumentType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private DocumentSource source;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private DocumentStatus status;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentChunk> chunks = new ArrayList<>();

    public Document(String title, String content, DocumentType type, DocumentSource source) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.source = source;
        this.status = DocumentStatus.DRAFT;
    }

    // --- Behavior Methods (Rich Domain Model) ---

    public void markAsProcessing() {
        if (this.status != DocumentStatus.DRAFT && this.status != DocumentStatus.FAILED) {
            throw new IllegalStateException("Chỉ có thể bắt đầu xử lý document ở trạng thái DRAFT hoặc FAILED");
        }
        this.status = DocumentStatus.PROCESSING;
    }

    public void addChunk(String chunkText, int chunkIndex, int tokenSize) {
        DocumentChunk chunk = new DocumentChunk(this, chunkText, chunkIndex, tokenSize);
        this.chunks.add(chunk);
    }

    public void markAsChunked() {
        if (this.status != DocumentStatus.PROCESSING) {
            throw new IllegalStateException("Document phải đang ở trạng thái PROCESSING mới có thể đánh dấu là CHUNKED");
        }
        this.status = DocumentStatus.CHUNKED;
    }

    public void markAsEmbedded() {
        if (this.status != DocumentStatus.CHUNKED) {
            throw new IllegalStateException("Document phải ở trạng thái CHUNKED mới có thể cập nhật thành EMBEDDED");
        }
        this.status = DocumentStatus.EMBEDDED;
    }

    public void markAsFailed() {
        this.status = DocumentStatus.FAILED;
    }
}
