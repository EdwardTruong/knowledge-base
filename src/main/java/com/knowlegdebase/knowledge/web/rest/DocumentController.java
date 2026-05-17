package com.knowlegdebase.knowledge.web.rest;

import com.knowlegdebase.knowledge.web.rest.dto.UploadDocumentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/knowledge/documents")
public interface DocumentController {

    @PostMapping
    ResponseEntity<UUID> uploadDocument(@Valid @RequestBody UploadDocumentRequest request);
}