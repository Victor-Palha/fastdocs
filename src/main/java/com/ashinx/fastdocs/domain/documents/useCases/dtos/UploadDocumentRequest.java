package com.ashinx.fastdocs.domain.documents.useCases.dtos;

import com.ashinx.fastdocs.domain.documents.enums.DocumentVisibility;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UploadDocumentRequest(
        MultipartFile file,
        DocumentVisibility documentVisibility,
        UUID userId
) {}
