package com.xlillium.kata_natixis_backend.controllers;

import com.xlillium.kata_natixis_backend.models.Book;
import com.xlillium.kata_natixis_backend.repositories.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
