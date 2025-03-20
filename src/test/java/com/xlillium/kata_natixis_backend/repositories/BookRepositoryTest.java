package com.xlillium.kata_natixis_backend.repositories;

import com.xlillium.kata_natixis_backend.BaseIntegrationTest;
import com.xlillium.kata_natixis_backend.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }
}
