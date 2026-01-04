package com.ashinx.fastdocs.domain.documents.useCases;

import com.ashinx.fastdocs.domain.documents.bucket.BucketService;
import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.domain.documents.exceptions.CompanyNotFoundException;
import com.ashinx.fastdocs.domain.documents.gateway.UserGateway;
import com.ashinx.fastdocs.domain.documents.repositories.DocumentRepository;
import com.ashinx.fastdocs.domain.documents.useCases.dtos.UploadDocumentRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadDocumentUseCase {
    final private BucketService bucketService;
    final private UserGateway userGateway;
    final private DocumentRepository documentRepository;

    public UploadDocumentUseCase(BucketService bucketService,  UserGateway userGateway,  DocumentRepository documentRepository) {
        this.bucketService = bucketService;
        this.userGateway = userGateway;
        this.documentRepository = documentRepository;
    }

    public DocumentEntity execute(UploadDocumentRequest data)  throws IOException {
        Optional<UUID> companyId = this.userGateway.findUserCompanyByUserId(data.userId());
        if (companyId.isEmpty()) {
            throw new CompanyNotFoundException();
        }

        String fileName = this.bucketService.generateFileName(data.file());
        String url = this.bucketService.upload(data.file(), fileName);
        DocumentEntity document = DocumentEntity.create(
                fileName,
                url,
                data.file().getContentType(),
                data.file().getSize(),
                data.documentVisibility(),
                companyId.get()
        );
        return this.documentRepository.create(document);
    }
}
