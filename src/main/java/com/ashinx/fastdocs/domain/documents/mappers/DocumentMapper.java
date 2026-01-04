package com.ashinx.fastdocs.domain.documents.mappers;

import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.infra.persistence.models.DocumentModel;

public abstract class DocumentMapper {
    public static DocumentModel toPersistence(DocumentEntity data) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setId(data.getId());
        documentModel.setFileName(data.getFileName());
        documentModel.setFileSize(data.getFileSize());
        documentModel.setUrl(data.getUrl());
        documentModel.setMimeType(data.getMimeType());
        documentModel.setVisibility(data.getVisibility());
        documentModel.setCompanyId(data.getCompanyId());
        return documentModel;
    }

    public static DocumentEntity toDomain(DocumentModel documentModel) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setId(documentModel.getId());
        documentEntity.setFileName(documentModel.getFileName());
        documentEntity.setFileSize(documentModel.getFileSize());
        documentEntity.setUrl(documentModel.getUrl());
        documentEntity.setMimeType(documentModel.getMimeType());
        documentEntity.setVisibility(documentModel.getVisibility());
        documentEntity.setCompanyId(documentModel.getCompanyId());
        return documentEntity;
    }
}
