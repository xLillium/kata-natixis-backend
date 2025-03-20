package com.xlillium.kata_natixis_backend.controllers;

import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.services.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        return bookService.searchBooks(title, author);
    }
}
