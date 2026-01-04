package com.ashinx.fastdocs.infra.persistence.models;

import com.ashinx.fastdocs.domain.documents.enums.DocumentVisibility;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "tb_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentModel {
    @Id
    private UUID id;

    private String fileName;

    private String url;

    private String mimeType;

    private Long fileSize;

    private DocumentVisibility visibility;
    
    private UUID companyId;
}
