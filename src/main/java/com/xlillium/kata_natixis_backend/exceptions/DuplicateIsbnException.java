package com.xlillium.kata_natixis_backend.exceptions;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
