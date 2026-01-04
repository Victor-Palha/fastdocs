package com.ashinx.fastdocs.infra.controllers.documents;

import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.domain.documents.useCases.FetchDocumentsUseCase;
import com.ashinx.fastdocs.domain.documents.useCases.dtos.FetchDocumentsRequest;
import com.ashinx.fastdocs.infra.controllers.documents.dtos.DocumentPaginationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FetchDocumentsController {
    final private FetchDocumentsUseCase fetchDocumentsUseCase;
    public FetchDocumentsController(FetchDocumentsUseCase fetchDocumentsUseCase) {
        this.fetchDocumentsUseCase = fetchDocumentsUseCase;
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentEntity>> execute(
        @AuthenticationPrincipal Jwt jwt,
        @Valid DocumentPaginationDTO data
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        FetchDocumentsRequest params = new FetchDocumentsRequest(
                userId,
                data.page(),
                data.offset()
        );

        List<DocumentEntity> documents = fetchDocumentsUseCase.execute(params);
        return ResponseEntity.ok(documents);
    }
}
