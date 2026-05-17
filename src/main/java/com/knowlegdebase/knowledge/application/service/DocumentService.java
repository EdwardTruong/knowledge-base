package com.knowlegdebase.knowledge.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.knowlegdebase.knowledge.domain.model.Document;

public interface DocumentService {
    Document save(Document document);

    Optional<Document> findById(UUID id);

    List<Document> findAll();

    Document findByIdOrThrow(UUID id);

    void softDelete(UUID id);

    void hardDelete(UUID id);

}
