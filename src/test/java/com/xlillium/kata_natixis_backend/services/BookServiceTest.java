package com.xlillium.kata_natixis_backend.services;

import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.mappers.BookMapper;
import com.xlillium.kata_natixis_backend.models.Book;
import com.xlillium.kata_natixis_backend.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testFindAllBooks_ReturnsDTOList() {
        List<Book> books = List.of(
            new Book("1984", "George Orwell", "9780155658110", true),
            new Book("Clean Code", "Robert C. Martin", "9780132350884", false)
        );

        List<BookDTO> expectedDTOs = List.of(
            new BookDTO(null, "1984", "George Orwell", "9780155658110", true),
            new BookDTO(null, "Clean Code", "Robert C. Martin", "9780132350884", false)
        );

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDto(any(Book.class))).thenReturn(
            new BookDTO(null, "1984", "George Orwell", "9780155658110", true),
            new BookDTO(null, "Clean Code", "Robert C. Martin", "9780132350884", false)
        );

        List<BookDTO> result = bookService.findAllBooks();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expectedDTOs);
    }

    @Test
    void testSearchBooks_ByTitleOnly() {
        bookService.searchBooks("java", null);

        verify(bookRepository).findByTitleContainingIgnoreCase(eq("java"));
        verify(bookRepository, never()).findByAuthorContainingIgnoreCase(anyString());
        verify(bookRepository, never()).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void testSearchBooks_ByAuthorOnly() {
        bookService.searchBooks(null, "martin");

        verify(bookRepository).findByAuthorContainingIgnoreCase(eq("martin"));
        verify(bookRepository, never()).findByTitleContainingIgnoreCase(anyString());
        verify(bookRepository, never()).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void testSearchBooks_ByTitleAndAuthor() {
        bookService.searchBooks("java", "bloch");

        verify(bookRepository).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("java", "bloch");
    }

    @Test
    void testSearchBooks_NoneProvided() {
        bookService.searchBooks(null, null);

        verify(bookRepository).findAll();
    }
}