package com.xlillium.kata_natixis.exceptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}
