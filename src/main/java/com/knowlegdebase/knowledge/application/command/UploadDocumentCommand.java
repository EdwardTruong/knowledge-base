package com.knowlegdebase.knowledge.application.command;

import com.knowlegdebase.knowledge.domain.model.DocumentSource;
import com.knowlegdebase.knowledge.domain.model.DocumentType;
import com.knowlegdebase.shared.cqrs.CommandWithResult;

import java.util.UUID;

public record UploadDocumentCommand(
        String title,
        String content,
        DocumentType type,
        DocumentSource source
) implements CommandWithResult<UUID> {
}
