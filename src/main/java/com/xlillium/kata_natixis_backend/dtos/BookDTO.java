package com.xlillium.kata_natixis_backend.dtos;

import com.xlillium.kata_natixis_backend.validation.OnCreate;
import com.xlillium.kata_natixis_backend.validation.OnPatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public record BookDTO(
        @Null(message = "ID must not be provided", groups = {OnCreate.class, OnPatch.class})
        Long id,

        @NotBlank(message = "Title is mandatory.", groups = {OnCreate.class})
        @Null(message = "Title must not be provided when updating a book", groups = {OnPatch.class})
        String title,
        @NotBlank(message = "Author is mandatory.", groups = {OnCreate.class})
        @Null(message = "Author must not be provided when updating a book", groups = {OnPatch.class})
        String author,
        @NotBlank(message = "ISBN is mandatory.", groups = {OnCreate.class})
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN should be either 10 or 13 digits", groups = {OnCreate.class})
        @Null(message = "ISBN must not be provided when updating a book", groups = {OnPatch.class})
        String isbn,
        @NotNull(message = "Borrowed status is mandatory.", groups = {OnCreate.class, OnPatch.class})
        Boolean borrowed
) {
}
