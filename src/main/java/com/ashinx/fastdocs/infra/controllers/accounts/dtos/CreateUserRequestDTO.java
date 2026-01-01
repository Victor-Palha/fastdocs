package com.ashinx.fastdocs.infra.controllers.accounts.dtos;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequestDTO(
        @Email
        String email,
        @Length(min = 6)
        String username
) {}
