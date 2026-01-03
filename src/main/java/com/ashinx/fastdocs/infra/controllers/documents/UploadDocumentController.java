package com.ashinx.fastdocs.infra.controllers.documents;

import com.ashinx.fastdocs.domain.documents.entities.DocumentEntity;
import com.ashinx.fastdocs.domain.documents.enums.DocumentVisibility;
import com.ashinx.fastdocs.domain.documents.useCases.UploadDocumentUseCase;
import com.ashinx.fastdocs.domain.documents.useCases.dtos.UploadDocumentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping()
public class UploadDocumentController {
    private final UploadDocumentUseCase uploadDocumentUseCase;

    public UploadDocumentController(UploadDocumentUseCase uploadDocumentUseCase) {
        this.uploadDocumentUseCase = uploadDocumentUseCase;
    }

    @PostMapping("/documents/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> execute(
            @RequestParam("file") MultipartFile file,
            @RequestParam("visibility") DocumentVisibility visibility,
            @AuthenticationPrincipal Jwt jwt
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Arquivo não pode ser vazio"));
            }

            UUID userId = UUID.fromString(jwt.getSubject());
            UploadDocumentRequest params = new UploadDocumentRequest(
                    file,
                    visibility,
                    userId
            );

            DocumentEntity documentCreated = this.uploadDocumentUseCase.execute(params);
            return ResponseEntity.status(HttpStatus.CREATED).body(documentCreated);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao fazer upload: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Dados inválidos: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro inesperado: " + e.getMessage()));
        }
    }
}