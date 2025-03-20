package com.xlillium.kata_natixis_backend.dtos;

public record BookDTO(
        Long id,
        String title,
        String author,
        String isbn,
        boolean borrowed
) {
}
