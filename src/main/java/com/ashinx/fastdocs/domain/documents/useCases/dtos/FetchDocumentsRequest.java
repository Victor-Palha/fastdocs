package com.ashinx.fastdocs.domain.documents.useCases.dtos;

import com.ashinx.fastdocs.domain.accounts.enums.UserRole;

import java.util.UUID;

public record FetchDocumentsRequest(
        UUID userId,
        UserRole role,
        Integer page,
        Integer offset
) {}
