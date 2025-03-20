package com.xlillium.kata_natixis_backend.controllers;

import com.xlillium.kata_natixis_backend.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class BookControllerTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("1984"))
                .andExpect(jsonPath("$[0].author").value("George Orwell"))
                .andExpect(jsonPath("$[0].isbn").value("9780155658110"))
                .andExpect(jsonPath("$[0].borrowed").value("true"));
    }

    @Test
    public void testGetBooksByTitle() throws Exception {
        mockMvc.perform(get("/api/books")
                        .param("title", "clean"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].isbn").value("9780132350884"))
                .andExpect(jsonPath("$[0].borrowed").value("false"));
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        mockMvc.perform(get("/api/books")
                        .param("author", "ober"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].isbn").value("9780132350884"))
                .andExpect(jsonPath("$[0].borrowed").value("false"));
    }

    @Test
    public void testGetBooksByTitleAndAuthor() throws Exception {
        mockMvc.perform(get("/api/books")
                        .param("title", "code")
                        .param("author", "ober"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"))
                .andExpect(jsonPath("$[0].isbn").value("9780132350884"))
                .andExpect(jsonPath("$[0].borrowed").value("false"));
    }

    @Test
    void testCreateBook_Success() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "Title",
                                      "author": "Author",
                                      "isbn": "1234567890",
                                      "borrowed": false
                                    }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.author").value("Author"))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.borrowed").value(false));
    }

    @Test
    void testCreateBook_DuplicateIsbn() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "Title",
                                      "author": "Author",
                                      "isbn": "9780155658110",
                                      "borrowed": false
                                    }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("A book with ISBN 9780155658110 already exists."));
    }

    @Test
    void testCreateBook_ValidationError() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "",
                                      "author": "",
                                      "isbn": "notvalid",
                                      "borrowed": null
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed. See 'details' for more informations."))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void testCreateBook_IdProvided() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "id": 1,
                                      "title": "Test",
                                      "author": "Author",
                                      "isbn": "1234567890",
                                      "borrowed": false
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].field").value("id"))
                .andExpect(jsonPath("$.details[0].message").value("ID must not be provided"));
    }

    @Test
    void testCreateBook_UnknownPropertyError() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "",
                                      "author": "",
                                      "isbn": "1234567890",
                                      "borrowed": false,
                                      "unknown": true
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request body is missing or malformed."))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void testCreateBook_InvalidFieldFormatError() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "title": "",
                                      "author": "",
                                      "isbn": "1234567890",
                                      "borrowed": "wrongType"
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request body is missing or malformed."))
                .andExpect(jsonPath("$.details").isArray());
    }
}