package com.library.demo.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import com.library.controller.BooksController;
import com.library.entity.Books;
import com.library.service.BooksServiceImpl;
import com.library.service.GoogleBooksServiceImpl;
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
public class BooksControllerTest {

    @Mock
    private BooksServiceImpl booksService;

    @Mock
    private GoogleBooksServiceImpl googleBooksService;

    @InjectMocks
    private BooksController booksController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowBookManagementPage() {
        Model model = new ConcurrentModel();
        String viewName = booksController.showBookManagementPage(model);
        assertEquals("bookManagementPage", viewName);
        assertTrue(model.containsAttribute("addBook"));
    }

    @Test
    public void testShowAddBookPage() {
        Model model = new ConcurrentModel();
        String viewName = booksController.showAddBookPage(model);
        assertEquals("bookManagementPage", viewName);
        assertTrue(model.containsAttribute("addBook"));
    }

    @Test
    public void testSubmitAddBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = booksController.create(new Books(), bindingResult, redirectAttributes, model, null, null);
        assertEquals("bookManagementPage", viewName);
        verify(booksService, times(0)).create(any(Books.class));
    }

    @Test
    public void testSubmitAddBook_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = booksController.create(new Books(), bindingResult, redirectAttributes, model, null, null);
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).create(any(Books.class));
    }

    @Test
    public void testShowUpdateBookPage() {
        Model model = new ConcurrentModel();
        String viewName = booksController.showUpdateBookPage(model);
        assertEquals("updateBookPage", viewName);
        assertTrue(model.containsAttribute("updateBook"));
    }

    @Test
    public void testSubmitUpdateBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class); 

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = booksController.update(new Books(), bindingResult, redirectAttributes, model, null);
        assertEquals("updateBookPage", viewName);
        verify(booksService, times(0)).update(anyInt(), any(Books.class));
    }

    @Test
    public void testSubmitUpdateBook_Success() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes =  mock(RedirectAttributes.class);
        Model model = mock(Model.class); 
        when(bindingResult.hasErrors()).thenReturn(false); 
        Books updateBook = new Books();
        updateBook.setId(1);
        // Act
        String viewName = booksController.update(updateBook, bindingResult, redirectAttributes, model, null);
        // Assert
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).update(eq(1), eq(updateBook)); 
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Updated Book!"); 
                                                                                                         
    }

    @Test
    public void testShowDeleteBookPage() {
        // Arrange
        Model model = new ConcurrentModel();
        // Act
        String viewName = booksController.showDeleteBookPage(model);
        // Assert
        assertEquals("deleteBookPage", viewName);
        assertTrue(model.containsAttribute("deleteBook"));
    }
    @Test
    public void testSubmitDeleteBook_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class); 

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = booksController.delete(new Books(), bindingResult, redirectAttributes, model, null, null);
        assertEquals("deleteBookPage", viewName);
        verify(booksService, times(0)).delete(anyInt());
    }

    @Test
    public void testSubmitDeleteBook_Success() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class); 

        when(bindingResult.hasErrors()).thenReturn(false);

        Books deleteBook = new Books();
        deleteBook.setId(1);
        String viewName = booksController.delete(deleteBook, bindingResult, redirectAttributes, model, null, null);
        assertEquals("redirect:/librarianHomePage", viewName);
        verify(booksService, times(1)).delete(eq(1));
    }

    @Test
    public void testShowAllBookPage() {
        Model model = new ConcurrentModel();
        List<Books> books = Collections.singletonList(new Books());
        when(booksService.getAll()).thenReturn(books);

        String viewName = booksController.showAllBookPage(model);
        assertEquals("allBooksPage", viewName);
        assertTrue(model.containsAttribute("books"));
        verify(booksService, times(1)).getAll();
    }

    @Test
    public void testGetBookDetails_Success() {
        Books book = new Books();
        book.setId(1);
        Optional<Books> optionalBook = Optional.of(book);
        when(booksService.getById(1)).thenReturn(optionalBook);

        ResponseEntity<Books> response = booksController.getBookDetails(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(booksService, times(1)).getById(1);
    }

    @Test
    public void testGetBookDetails_NotFound() {
        when(booksService.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<Books> response = booksController.getBookDetails(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(booksService, times(1)).getById(1);
    }

}
