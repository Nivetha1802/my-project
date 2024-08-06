package com.library.demo.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.library.controller.LibrarianController;
import com.library.entity.Books;
import com.library.service.BooksService;
import com.library.service.GoogleBooksService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class LibrarianControllerTest {

    @Mock
    private BooksService booksService;

    @Mock
    private GoogleBooksService googleBooksService;

    @InjectMocks
    private LibrarianController librarianController;


     @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowBookManagementPage() {
        Model model = new ConcurrentModel();
        String viewName = librarianController.showBookManagementPage(model);
        assertEquals("bookManagement", viewName);
        assertTrue(model.containsAttribute("addBook"));
    }

    @Test
    public void testShowAddBookPage() {
        Model model = new ConcurrentModel();
        String viewName = librarianController.showAddBookPage(model);
        assertEquals("bookManagement", viewName);
        assertTrue(model.containsAttribute("addBook"));
    }

    @Test
    public void testSubmitAddBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String viewName = librarianController.submitAddBook(new Books(), bindingResult, redirectAttributes, model);
        assertEquals("redirect:/bookManagement", viewName);
        verify(booksService, times(0)).createBook(any(Books.class));
    }

    @Test
    public void testSubmitAddBook_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        when(bindingResult.hasErrors()).thenReturn(false);
        
        String viewName = librarianController.submitAddBook(new Books(), bindingResult, redirectAttributes, model);
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).createBook(any(Books.class));
    }

    @Test
    public void testShowUpdateBookPage() {
        Model model = new ConcurrentModel();
        String viewName = librarianController.showUpdateBookPage(model);
        assertEquals("updateBook", viewName);
        assertTrue(model.containsAttribute("updateBook"));
    }

    @Test
    public void testSubmitUpdateBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String viewName = librarianController.submitUpdateBook(new Books(), redirectAttributes, bindingResult);
        assertEquals("redirect:/updateBook", viewName);
        verify(booksService, times(0)).updateBook(anyInt(), any(Books.class));
    }

    @Test
    public void testSubmitUpdateBook_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(bindingResult.hasErrors()).thenReturn(false);
        
        Books updateBook = new Books();
        updateBook.setBookid(1);
        String viewName = librarianController.submitUpdateBook(updateBook, redirectAttributes, bindingResult);
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).updateBook(eq(1), any(Books.class));
    }

    @Test
    public void testShowDeleteBookPage() {
        Model model = new ConcurrentModel();
        String viewName = librarianController.showDeleteBookPage(model);
        assertEquals("deleteBook", viewName);
        assertTrue(model.containsAttribute("deleteBook"));
    }

    @Test
    public void testSubmitDeleteBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String viewName = librarianController.submitDeleteBook(new Books(), redirectAttributes, bindingResult);
        assertEquals("redirect:/deleteBook", viewName);
        verify(booksService, times(0)).deleteBook(anyInt());
    }

    @Test
    public void testSubmitDeleteBook_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(bindingResult.hasErrors()).thenReturn(false);
        
        Books deleteBook = new Books();
        deleteBook.setBookid(1);
        String viewName = librarianController.submitDeleteBook(deleteBook, redirectAttributes, bindingResult
        );
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).deleteBook(eq(1));
    }

    @Test
    public void testShowAllBookPage() {
        Model model = new ConcurrentModel();
        List<Books> books = Collections.singletonList(new Books());
        when(booksService.getAllBooks()).thenReturn(books);

        String viewName = librarianController.showAllBookPage(model);
        assertEquals("allBooks", viewName);
        assertTrue(model.containsAttribute("books"));
        verify(booksService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookDetails_Success() {
        Books book = new Books();
        book.setBookid(1);
        when(booksService.getBookById(1)).thenReturn(book);

        ResponseEntity<Books> response = librarianController.getBookDetails(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(booksService, times(1)).getBookById(1);
    }

    @Test
    public void testGetBookDetails_NotFound() {
        when(booksService.getBookById(1)).thenReturn(null);

        ResponseEntity<Books> response = librarianController.getBookDetails(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(booksService, times(1)).getBookById(1);
    }

    
}
