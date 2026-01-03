package com.ashinx.fastdocs.domain.documents.entities;

import com.ashinx.fastdocs.domain.documents.enums.DocumentVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {
    private UUID id;
    private String fileName;
    private String url;
    private String mimeType;
    private Long fileSize;
    private DocumentVisibility visibility;
    private UUID companyId;

    public static DocumentEntity create(String fileName, String url, String mimeType, Long fileSize, DocumentVisibility visibility, UUID companyId) {
        UUID id = UUID.randomUUID();
        return new DocumentEntity(id, fileName, url, mimeType, fileSize, visibility, companyId);
    }

    public static DocumentEntity build(UUID id, String fileName, String url, String mimeType, Long fileSize, DocumentVisibility visibility, UUID companyId) {
        return new DocumentEntity(id, fileName, url, mimeType, fileSize, visibility, companyId);
    }
}
