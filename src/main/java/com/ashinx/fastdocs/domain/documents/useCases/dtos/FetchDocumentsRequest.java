package com.ashinx.fastdocs.domain.documents.useCases.dtos;

import java.util.UUID;

public record FetchDocumentsRequest(
        UUID userId,
        Integer page,
        Integer offset
) {}
