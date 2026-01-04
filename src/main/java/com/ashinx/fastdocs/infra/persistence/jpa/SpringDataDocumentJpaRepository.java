package com.ashinx.fastdocs.infra.persistence.jpa;

import com.ashinx.fastdocs.infra.persistence.models.DocumentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataDocumentJpaRepository extends JpaRepository<DocumentModel, UUID> {
    Page<DocumentModel> findAllByCompanyId(UUID companyId, Pageable pageable);
}
