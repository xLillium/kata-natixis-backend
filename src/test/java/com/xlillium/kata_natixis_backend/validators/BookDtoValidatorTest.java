package com.xlillium.kata_natixis_backend.validators;

import com.xlillium.kata_natixis_backend.BaseIntegrationTest;
import com.xlillium.kata_natixis_backend.dtos.BookDTO;
import com.xlillium.kata_natixis_backend.validation.OnCreate;
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
    public void whenValidDataOnCreate_thenNoViolations() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "1234567890", false);

        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIdNotNullOnCreate_thenViolation() {
        BookDTO bookDTO = new BookDTO(1L, "title", "author", "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ID must not be provided");
    }

    @Test
    public void whenNullTitleOnCreate_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, null, "author", "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Title is mandatory.");
    }

    @Test
    public void whenNullAuthorOnCreate_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", null, "1234567890", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("Author is mandatory.");
    }

    @Test
    public void whenNullIsbnOnCreate_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", null, false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN is mandatory.");
    }

    @Test
    public void whenIsbnLengthOf13OnCreate_thenValid() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "1234567890123", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsbnLengthDifferentThan10or13OnCreate_thenViolation() {
        BookDTO bookDTO = new BookDTO(null, "title", "author", "12345678901", false);
        Set<ConstraintViolation<BookDTO>> violations = validator.validate(bookDTO, OnCreate.class);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").containsExactly("ISBN should be either 10 or 13 digits");
    }

}

