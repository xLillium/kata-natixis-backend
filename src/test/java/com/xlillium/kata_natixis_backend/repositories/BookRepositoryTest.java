package com.xlillium.kata_natixis_backend.repositories;

import com.xlillium.kata_natixis_backend.BaseIntegrationTest;
import com.xlillium.kata_natixis_backend.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        List<Book> results = bookRepository.findByTitleContainingIgnoreCase("198");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("1984", results.get(0).getTitle());
    }

    @Test
    void testFindByAuthorContainingIgnoreCase() {
        List<Book> results = bookRepository.findByAuthorContainingIgnoreCase("martin");
        assertFalse(results.isEmpty());
        assertEquals("Robert C. Martin", results.get(0).getAuthor());
    }

    @Test
    void testFindByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase() {
        List<Book> results = bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("clean", "robert");
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());
    }
}
