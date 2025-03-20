package com.xlillium.kata_natixis_backend;

import com.xlillium.kata_natixis_backend.models.Book;
import com.xlillium.kata_natixis_backend.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {
    @Autowired
    private BookRepository bookRepository;
    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        bookRepository.save(new Book("1984", "George Orwell", "9780155658110", true));
        bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", false));
    }
}
