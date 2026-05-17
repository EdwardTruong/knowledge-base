package com.knowlegdebase.knowledge.application.command;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import com.knowlegdebase.knowledge.application.service.DocumentService;
import com.knowlegdebase.knowledge.domain.model.Document;
import com.knowlegdebase.shared.cqrs.CommandWithResultHandler;
import com.knowlegdebase.shared.cqrs.annotation.CqrsCommandWithResultHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CqrsCommandWithResultHandler(UploadDocumentCommand.class)
public class UploadDocumentCommandHandler implements CommandWithResultHandler<UploadDocumentCommand, UUID> {

    private final DocumentService documentService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public UUID handle(UploadDocumentCommand command) {
        // Tạo Rich Entity
        Document document = Document.builder()
                .title(command.title())
                .content(command.content())
                .type(command.type())
                .source(command.source())
                .build();
        
        // Lưu qua Domain Service
        Document savedDocument = documentService.save(document);
        
        // Phát Event để tiến trình nền (Chunking/Embedding) bắt đầu chạy
        eventPublisher.publishEvent(new DocumentCreatedEvent(savedDocument.getId()));
        
        return savedDocument.getId();
    }
}
