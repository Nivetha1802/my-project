package com.library.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.library.Dto.GoogleBooks;
import com.library.service.GoogleBooksServiceImpl;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GoogleBooksServiceTest {

    @InjectMocks
    private GoogleBooksServiceImpl googleBooksService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchBook() throws Exception {
        // Arrange
        String jsonResponse = "{ \"items\": [ { \"id\": \"1\", \"volumeInfo\": { \"title\": \"Book Title\", \"authors\": [ \"Author 1\", \"Author 2\" ], \"publisher\": \"Publisher Name\", \"publishedDate\": \"2024-08-03\", \"description\": \"Book Description\", \"imageLinks\": { \"thumbnail\": \"http://example.com/thumbnail.jpg\" } } } ] }";
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(ResponseEntity.ok(jsonResponse));

        // Act
        List<GoogleBooks> books = googleBooksService.searchBook("test query");

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());

        GoogleBooks book = books.get(0);
        assertEquals("Book Title", book.getTitle());
        assertEquals("Author 1, Author 2", book.getAuthors());
        assertEquals("Publisher Name", book.getPublisher());
        assertEquals("2024-08-03", book.getPublishedDate());
        assertEquals("Book Description", book.getDescription());
        assertEquals("http://example.com/thumbnail.jpg", book.getThumbnail());
    }

    @Test
    public void testParseBooks() throws Exception {
        // Arrange
        String jsonResponse = "{ \"items\": [ { \"id\": \"2\", \"volumeInfo\": { \"title\": \"Another Book\", \"authors\": [ \"Another Author\" ], \"publisher\": \"Another Publisher\", \"publishedDate\": \"2024-07-01\", \"description\": \"Another Description\", \"imageLinks\": { \"thumbnail\": \"http://example.com/another-thumbnail.jpg\" } } } ] }";

        // Act
        List<GoogleBooks> books = googleBooksService.parseBooks(jsonResponse);

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());

        GoogleBooks book = books.get(0);
        assertEquals("Another Book", book.getTitle());
        assertEquals("Another Author", book.getAuthors());
        assertEquals("Another Publisher", book.getPublisher());
        assertEquals("2024-07-01", book.getPublishedDate());
        assertEquals("Another Description", book.getDescription());
        assertEquals("http://example.com/another-thumbnail.jpg", book.getThumbnail());
    }
}
