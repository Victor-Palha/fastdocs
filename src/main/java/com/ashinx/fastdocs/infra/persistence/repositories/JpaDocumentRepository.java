package com.ashinx.fastdocs.infra.persistence.repositories;

import com.ashinx.fastdocs.domain.accounts.enums.UserRole;
import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.domain.documents.enums.DocumentVisibility;
import com.ashinx.fastdocs.domain.documents.mappers.DocumentMapper;
import com.ashinx.fastdocs.domain.documents.repositories.DocumentRepository;
import com.ashinx.fastdocs.infra.persistence.jpa.SpringDataDocumentJpaRepository;
import com.ashinx.fastdocs.infra.persistence.models.DocumentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaDocumentRepository implements DocumentRepository {

    private final SpringDataDocumentJpaRepository jpaRepo;

    public JpaDocumentRepository(SpringDataDocumentJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public DocumentEntity create(DocumentEntity data) {
        DocumentModel documentModel = DocumentMapper.toPersistence(data);
        jpaRepo.save(documentModel);
        return data;
    }

    @Override
    public DocumentEntity findById(UUID id) {
        return null;
    }

    @Override
    public List<DocumentEntity> findAll(UUID companyId, UserRole role, Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<DocumentModel> results;

        if (role == UserRole.ADMIN) {
            results = this.jpaRepo.findAllByCompanyId(companyId, pageable);
        } else {
            results = this.jpaRepo.findAllByCompanyIdAndVisibility(
                    companyId,
                    DocumentVisibility.PUBLIC,
                    pageable
            );
        }

        return results.getContent()
                .stream()
                .map(DocumentMapper::toDomain)
                .collect(Collectors.toList());
    }
}
