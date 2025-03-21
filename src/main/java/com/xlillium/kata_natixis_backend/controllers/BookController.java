package com.xlillium.kata_natixis_backend.controllers;

import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.services.BookService;
import com.xlillium.kata_natixis_backend.validation.OnCreate;
import com.xlillium.kata_natixis_backend.validation.OnPatch;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@Validated(OnCreate.class) @RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @PatchMapping("/{bookId}")
    public BookDTO patchBook(@PathVariable Long bookId, @Validated(OnPatch.class) @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }
}
