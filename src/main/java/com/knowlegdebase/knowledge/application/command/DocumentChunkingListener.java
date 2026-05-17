package com.knowlegdebase.knowledge.application.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.knowlegdebase.knowledge.application.service.DocumentService;
import com.knowlegdebase.knowledge.domain.model.Document;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentChunkingListener {

    private static final Logger log = LoggerFactory.getLogger(DocumentChunkingListener.class);
    private final DocumentService documentService;

    // @ApplicationModuleListener tự động xử lý event bất đồng bộ sau khi transaction gốc commit thành công
    @ApplicationModuleListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onDocumentCreated(DocumentCreatedEvent event) {
        log.info("Bắt đầu tiến trình Chunking cho Document ID: {}", event.documentId());

        Document document = documentService.findByIdOrThrow(event.documentId());
        document.markAsProcessing();
        documentService.save(document); // Lưu trạng thái PROCESSING

        try {
            // Cấu hình Chunking của Spring AI
            TokenTextSplitter textSplitter = TokenTextSplitter.builder()
                    .withChunkSize(800)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();
            
            // Chuyển string sang định dạng Document của Spring AI
            org.springframework.ai.document.Document springAiDoc = new org.springframework.ai.document.Document(document.getContent());
            List<org.springframework.ai.document.Document> chunks = textSplitter.apply(List.of(springAiDoc));
            
            for (int i = 0; i < chunks.size(); i++) {
                document.addChunk(chunks.get(i).getText(), i, chunks.get(i).getText().length());
            }
            
            document.markAsChunked();
            documentService.save(document); // Cập nhật trạng thái CHUNKED và lưu các chunk con
            log.info("Hoàn tất Chunking cho Document ID: {} - Tạo ra {} chunks", event.documentId(), chunks.size());
        } catch (Exception e) {
            log.error("Lỗi khi chunking document ID: {}", event.documentId(), e);
            document.markAsFailed();
            documentService.save(document);
        }
    }
}