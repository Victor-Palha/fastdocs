package com.ashinx.fastdocs.domain.documents.repositories;

import com.ashinx.fastdocs.domain.accounts.enums.UserRole;
import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository {
    DocumentEntity create(DocumentEntity data);
    DocumentEntity findById(UUID id);
    List<DocumentEntity> findAll(UUID companyId, UserRole role, Integer page, Integer offset);
}
