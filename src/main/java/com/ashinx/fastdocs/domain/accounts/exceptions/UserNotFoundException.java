package com.ashinx.fastdocs.domain.accounts.exceptions;

import com.ashinx.fastdocs.core.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User Not Found");
    }
}
