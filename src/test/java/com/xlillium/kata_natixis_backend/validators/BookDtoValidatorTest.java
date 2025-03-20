package com.xlillium.kata_natixis_backend.validators;

import com.xlillium.kata_natixis_backend.BaseIntegrationTest;
import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDtoValidatorTest extends BaseIntegrationTest {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void whenValidData_thenNoViolations() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "1234567890", false);

        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIdNotNull_thenViolation() {
        BookDTO bookDTO = new BookDTO(1L, "title", "author", "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ID must not be provided for a new book");
    }

    @Test
    public void whenNullTitle_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, null, "author", "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Title is mandatory.");
    }

    @Test
    public void whenNullAuthor_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", null, "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Author is mandatory.");
    }

    @Test
    public void whenNullIsbn_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", null, false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN is mandatory.");
    }

    @Test
    public void whenIsbnLengthOf13_thenValid() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "1234567890123", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsbnLengthDifferentThan10or13_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "12345678901", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN should be either 10 or 13 digits");
    }

}

