package com.ashinx.fastdocs.domain.accounts.useCases.dtos;

import java.util.UUID;

public record AddUserRequest(
    String username,
    String email,
    UUID requesterId
) {}
