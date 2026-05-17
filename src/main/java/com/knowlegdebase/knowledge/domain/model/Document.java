package com.knowlegdebase.knowledge.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.knowlegdebase.shared.domain.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@SuperBuilder
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

    // --- Behavior Methods (Rich Domain Model) ---

    /**
     * Bắt đầu quá trình xử lý tài liệu (ví dụ: chia nhỏ, nhúng vector).
     * Trạng thái hợp lệ để bắt đầu là DRAFT (mới tạo) hoặc FAILED (thử lại).
     *
     * @throws IllegalStateException Nếu tài liệu không ở trạng thái hợp lệ.
     */
    public void markAsProcessing() {
        if (this.status != DocumentStatus.DRAFT && this.status != DocumentStatus.FAILED) {
            throw new IllegalStateException("Chỉ có thể bắt đầu xử lý document ở trạng thái DRAFT hoặc FAILED");
        }
        this.status = DocumentStatus.PROCESSING;
    }

    /**
     * Thêm một phân đoạn (chunk) văn bản mới thuộc về tài liệu này.
     *
     * @param chunkText Nội dung của phân đoạn văn bản.
     * @param chunkIndex Số thứ tự của phân đoạn trong tài liệu.
     * @param tokenSize Kích thước phân đoạn tính bằng số lượng token.
     */
    public void addChunk(String chunkText, int chunkIndex, int tokenSize) {
        DocumentChunk chunk = new DocumentChunk(this, chunkText, chunkIndex, tokenSize);
        this.chunks.add(chunk);
    }

    /**
     * Đánh dấu tài liệu đã hoàn tất quá trình chia nhỏ (chunking).
     *
     * @throws IllegalStateException Nếu tài liệu không ở trạng thái PROCESSING.
     */
    public void markAsChunked() {
        if (this.status != DocumentStatus.PROCESSING) {
            throw new IllegalStateException("Document phải đang ở trạng thái PROCESSING mới có thể đánh dấu là CHUNKED");
        }
        this.status = DocumentStatus.CHUNKED;
    }

    /**
     * Đánh dấu toàn bộ các phân đoạn (chunks) của tài liệu đã được nhúng 
     * (embedded) thành công vào Vector Database.
     *
     * @throws IllegalStateException Nếu tài liệu chưa hoàn tất việc chia nhỏ (CHUNKED).
     */
    public void markAsEmbedded() {
        if (this.status != DocumentStatus.CHUNKED) {
            throw new IllegalStateException("Document phải ở trạng thái CHUNKED mới có thể cập nhật thành EMBEDDED");
        }
        this.status = DocumentStatus.EMBEDDED;
    }

    /**
     * Đánh dấu quá trình xử lý tài liệu gặp lỗi và thất bại.
     */
    public void markAsFailed() {
        this.status = DocumentStatus.FAILED;
    }
}
