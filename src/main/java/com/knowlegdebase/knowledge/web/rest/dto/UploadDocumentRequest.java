package com.knowledgebase.knowledge.web.rest.dto;

import com.knowledgebase.knowledge.domain.model.DocumentSource;
import com.knowledgebase.knowledge.domain.model.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadDocumentRequest(
        @NotBlank(message = "Tiêu đề không được để trống") 
        String title,
        @NotBlank(message = "Nội dung không được để trống") 
        String content,
        @NotNull(message = "Loại tài liệu không được để trống") 
        DocumentType type,
        @NotNull(message = "Nguồn tài liệu không được để trống") 
        DocumentSource source
) {}