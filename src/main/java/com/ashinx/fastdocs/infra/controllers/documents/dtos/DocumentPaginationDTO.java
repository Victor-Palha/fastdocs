package com.ashinx.fastdocs.infra.controllers.documents.dtos;

import jakarta.validation.constraints.Min;

public record DocumentPaginationDTO(
        @Min(1)
        Integer page,
        @Min(1)
        Integer offset
) {}
