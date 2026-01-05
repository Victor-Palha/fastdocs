package com.ashinx.fastdocs.domain.documents.useCases;

import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.domain.documents.exceptions.CompanyNotFoundException;
import com.ashinx.fastdocs.domain.documents.gateway.UserGateway;
import com.ashinx.fastdocs.domain.documents.repositories.DocumentRepository;
import com.ashinx.fastdocs.domain.documents.useCases.dtos.FetchDocumentsRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FetchDocumentsUseCase {
    final private DocumentRepository documentRepository;
    final private UserGateway userGateway;

    public FetchDocumentsUseCase(DocumentRepository documentRepository,  UserGateway userGateway) {
        this.documentRepository = documentRepository;
        this.userGateway = userGateway;
    }

    public List<DocumentEntity> execute(FetchDocumentsRequest data) {
        Optional<UUID> companyId = this.userGateway.findUserCompanyByUserId(data.userId());
        if (companyId.isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return this.documentRepository.findAll(companyId.get(), data.role(), data.page(), data.offset());
    }
}
