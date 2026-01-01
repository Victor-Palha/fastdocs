package com.ashinx.fastdocs.domain.accounts.useCases.dtos;

public record CreateUserRequest(
        String username,
        String email
) {}
