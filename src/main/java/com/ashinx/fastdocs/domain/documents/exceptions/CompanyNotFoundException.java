package com.ashinx.fastdocs.domain.documents.exceptions;

import com.ashinx.fastdocs.core.exceptions.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException() {
        super("User Company Not Found");
    }
}
