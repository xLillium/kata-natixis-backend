package com.xlillium.kata_natixis_backend.services;

import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.exceptions.DuplicateIsbnException;
import com.xlillium.kata_natixis_backend.mappers.BookMapper;
import com.xlillium.kata_natixis_backend.models.Book;
import com.xlillium.kata_natixis_backend.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooks(String title, String author) {
        if ((title == null || title.isBlank()) && (author == null || author.isBlank())) {
            return findAllBooks();
        }

        List<Book> results;
        if (title != null && !title.isBlank() && author != null && !author.isBlank()) {
            results = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        } else if (title != null && !title.isBlank()) {
            results = bookRepository.findByTitleContainingIgnoreCase(title);
        } else {
            results = bookRepository.findByAuthorContainingIgnoreCase(author);
        }

        return results.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.isbn())) {
            throw new DuplicateIsbnException(bookDTO.isbn());
        }

        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }
}