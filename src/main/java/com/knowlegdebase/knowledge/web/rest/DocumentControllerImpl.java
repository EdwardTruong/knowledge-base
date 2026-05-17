package com.knowlegdebase.knowledge.web.rest;

import com.knowlegdebase.knowledge.application.command.UploadDocumentCommand;
import com.knowlegdebase.knowledge.web.rest.dto.UploadDocumentRequest;
import com.knowlegdebase.shared.cqrs.CqrsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final CqrsGateway cqrsGateway;

    @Override
    public ResponseEntity<UUID> uploadDocument(UploadDocumentRequest request) {
        // Map DTO sang Command
        UploadDocumentCommand command = new UploadDocumentCommand(
                request.title(),
                request.content(),
                request.type(),
                request.source()
        );
        
        // Gửi Command qua Gateway để xử lý và nhận lại ID của Document vừa tạo
        UUID documentId = cqrsGateway.send(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(documentId);
    }
}
