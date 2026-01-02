package com.ashinx.fastdocs.domain.accounts.exceptions;

import com.ashinx.fastdocs.core.exceptions.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
