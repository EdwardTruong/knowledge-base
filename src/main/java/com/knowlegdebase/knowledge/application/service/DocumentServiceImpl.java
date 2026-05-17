package com.knowlegdebase.knowledge.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.knowlegdebase.knowledge.domain.model.Document;
import com.knowlegdebase.knowledge.infrastructure.persistence.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document findByIdOrThrow(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài liệu với ID: " + id));
    }

    @Override
    public Optional<Document> findById(UUID id) {
        return documentRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAllByDeletedFalse();
    }

    @Override
    public void softDelete(UUID id) {
        documentRepository.findByIdAndDeletedFalse(id).ifPresent(document -> {
            document.markAsDeleted();
            documentRepository.save(document);
        });
    }

    @Override
    public void hardDelete(UUID id) {
        documentRepository.deleteById(id);
    }
}
