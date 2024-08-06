package com.library.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.library.entity.Books;
import com.library.repository.BooksRepository;
import com.library.service.BooksServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksServiceImpl booksService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        Books book1 = new Books(1, "Book 1", "Author 1", "Subject 1", "Info 1", 10);
        Books book2 = new Books(2, "Book 2", "Author 2", "Subject 2", "Info 2", 5);
        when(booksRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Books> result = booksService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getBookname());
        assertEquals("Book 2", result.get(1).getBookname());
    }

    @Test
    public void testGetBookById_BookExists() {
        // Arrange
        Books mockBook = new Books(1, "Book 1", "Author 1", "Subject 1", "Info 1", 10);
        when(booksRepository.findById(anyInt())).thenReturn(Optional.of(mockBook));

        // Act
        Books result = booksService.getBookById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBookid());
        assertEquals("Book 1", result.getBookname());
    }

    @Test
    public void testGetBookById_BookDoesNotExist() {
        // Arrange
        when(booksRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Books result = booksService.getBookById(1);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateBook() {
        // Arrange
        Books mockBook = new Books(1, "Book 1", "Author 1", "Subject 1", "Info 1", 10);
        when(booksRepository.save(any(Books.class))).thenReturn(mockBook);

        // Act
        Books result = booksService.createBook(mockBook);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBookid());
        assertEquals("Book 1", result.getBookname());
    }

    @Test
    public void testUpdateBook_BookExists() {
        // Arrange
        Books existingBook = new Books(1, "Book 1", "Author 1", "Subject 1", "Info 1", 10);
        Books updatedBook = new Books(1, "Updated Book 1", "Updated Author 1", "Updated Subject 1", "Updated Info 1", 15);
        when(booksRepository.findById(anyInt())).thenReturn(Optional.of(existingBook));
        when(booksRepository.save(any(Books.class))).thenReturn(updatedBook);

        // Act
        Books result = booksService.updateBook(1, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBookid());
        assertEquals("Updated Book 1", result.getBookname());
        assertEquals("Updated Author 1", result.getAuthor());
        assertEquals("Updated Subject 1", result.getSubject());
        assertEquals("Updated Info 1", result.getInfo());
        assertEquals(15, result.getBookcount());
    }

    @Test
    public void testUpdateBook_BookDoesNotExist() {
        // Arrange
        when(booksRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Books updatedBook = new Books(1, "Updated Book 1", "Updated Author 1", "Updated Subject 1", "Updated Info 1", 15);
        Books result = booksService.updateBook(1, updatedBook);

        // Assert
        assertNull(result);
    }

    @Test
    public void testDeleteBook() {
        // Act
        booksService.deleteBook(1);

        // Assert
        verify(booksRepository, times(1)).deleteById(1);
    }
}
