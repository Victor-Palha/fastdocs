package com.ashinx.fastdocs.core.exceptions;

public class ApplicationException extends RuntimeException {
    protected ApplicationException(String message) {
        super(message);
    }
}