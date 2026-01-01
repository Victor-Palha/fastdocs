package com.ashinx.fastdocs.domain.accounts.dtos;

public record CreateUserRequest(
        String username,
        String email
) {}
