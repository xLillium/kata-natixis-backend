package com.xlillium.kata_natixis_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public record BookDTO(
        @Null(message = "ID must not be provided for a new book")
        Long id,
        @NotBlank(message = "Title is mandatory.")
        String title,
        @NotBlank(message = "Author is mandatory.")
        String author,
        @NotBlank(message = "ISBN is mandatory.")
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN should be either 10 or 13 digits")
        String isbn,
        @NotNull(message = "Borrowed status is mandatory.")
        Boolean borrowed
) {
}
