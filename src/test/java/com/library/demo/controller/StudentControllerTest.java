package com.library.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.Dto.GoogleBooks;
import com.library.controller.LendController;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.service.GoogleBooksService;
import com.library.service.LendDetailsService;
import com.library.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private LendDetailsService lendDetailsService;

    @Mock
    private GoogleBooksService googleBooksService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private LendController studentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowLendtablePage_WithQuery() {
        String query = "test";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(googleBooksService.searchBook(query)).thenReturn(books);

        String viewName = studentController.showLendtablePage(query, model);

        assertEquals("lend_table", viewName);
        verify(model, times(1)).addAttribute("books", books);
        verify(model, times(1)).addAttribute("query", query);
    }

    @Test
    public void testShowLendtablePage_WithoutQuery() {
        String viewName = studentController.showLendtablePage(null, model);

        assertEquals("lend_table", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitLendTable() throws JsonProcessingException {
        String selectedBooks = "[{\"id\":\"HT0HEAAAQBAJ\",\"title\":\"The Archer\",\"authors\":\"Paulo Coelho\",\"publisher\":\"Penguin Random House India Private Limited\",\"publishedDate\":\"2020-11-16\"}]";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        doReturn(books).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        String viewName = studentController.submitLendTable(selectedBooks, session);

        assertEquals("redirect:/lendDetails", viewName);
        verify(session, times(1)).setAttribute("selectedBooks", books);
    }


    @Test
    public void testShowLendDetails() {
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(session.getAttribute("selectedBooks")).thenReturn(books);

        String viewName = studentController.showLendDetails(session, model);

        assertEquals("lend_details", viewName);
        verify(model, times(1)).addAttribute("selectedBooks", books);
    }

    @Test
    public void testSubmitLendDetail() throws Exception {
        // Arrange
        String selectedBooks = "[{\"lendId\":1,\"bookname\":\"Test Book\"}]";
        List<LendDetails> lendDetailsList = Collections.singletonList(new LendDetails());
        Integer userId = 1;
        UserEntity user = new UserEntity();
        Optional<UserEntity> optionalUser = Optional.of(user);
        doReturn(lendDetailsList).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        when(session.getAttribute("userId")).thenReturn(userId);
        when(userService.getById(userId)).thenReturn(optionalUser);
    
        // Act
        String viewName = studentController.submitLendDetail(selectedBooks, session, redirectAttributes);
    
        // Assert
        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).processBookLendDetails(any(LendDetails.class), eq(optionalUser));
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Lent Books!");
    }
    

    @Test
    public void testShowReturnBooksPage() {
        Integer userId = 1;
        List<LendDetails> lendBooks = Collections.singletonList(new LendDetails());
        when(session.getAttribute("userId")).thenReturn(userId);
        when(lendDetailsService.getLendDetailsByUserId(userId)).thenReturn(lendBooks);

        String viewName = studentController.showReturnBooksPage(model, session);

        assertEquals("return_table", viewName);
        verify(model, times(1)).addAttribute("lendBooks", lendBooks);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitReturnBooks() throws Exception {
        String selectedBooks = "[{\"lendId\":1,\"bookname\":\"Test Book\"}]";
        LendDetails lendDetails = new LendDetails();
        lendDetails.setLendId(1);
        List<LendDetails> lendDetailsList = Collections.singletonList(lendDetails);
        
        doReturn(lendDetailsList).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));
    
        String viewName = studentController.submitReturnBooks(selectedBooks, redirectAttributes, session);
    
        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).delete(eq(1));
        verify(session, times(1)).removeAttribute("selectedBooks");
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Returned Books!");
    }
    

    @Test
    public void testShowRenewtablePage() {
        Integer userId = 1;
        List<LendDetails> lendBooks = Collections.singletonList(new LendDetails());
        when(session.getAttribute("userId")).thenReturn(userId);
        when(lendDetailsService.getLendDetailsByUserId(userId)).thenReturn(lendBooks);

        String viewName = studentController.showRenewtablePage(model, session);

        assertEquals("renew_table", viewName);
        verify(model, times(1)).addAttribute("lendBooks", lendBooks);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitRenewtable() throws JsonProcessingException {
        String selectedBooks = "[{\"lendId\":1,\"bookname\":\"Test Book\"}]";
        LendDetails lendDetails = new LendDetails();
        lendDetails.setLendId(1);
        List<LendDetails> lendDetailsList = Collections.singletonList(lendDetails);
        doReturn(lendDetailsList).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        String viewName = studentController.submitRenewtable(selectedBooks, redirectAttributes);

        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).renewBook(anyInt());
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Renewed Books!");
    }

    @Test
    public void testShowFineDetailsPage() {
        Integer userId = 1;
        UserEntity user = new UserEntity();
        user.setName("Test User");

        LendDetails lendDetail = new LendDetails();
        lendDetail.setFine(10.0);

        List<LendDetails> lendBooks = Collections.singletonList(lendDetail);
        List<LendDetails> lendBooksWithFine = lendBooks.stream().filter(ld -> ld.getFine() > 0.0)
                .collect(Collectors.toList());
        Optional<UserEntity> optionalUser = Optional.of(user);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(userService.getById(userId)).thenReturn(optionalUser);
        when(lendDetailsService.getLendDetailsByUserId(userId)).thenReturn(lendBooks);

        String viewName = studentController.showFineDetailsPage(model, session);

        assertEquals("fine_table", viewName);
        verify(model, times(1)).addAttribute("books", lendBooksWithFine);
        verify(model, times(1)).addAttribute("user", "Test User");
    }

    @Test
    public void testSearch_WithQuery() {
        String query = "test";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(googleBooksService.searchBook(query)).thenReturn(books);

        String viewName = studentController.search(query, model);

        assertEquals("search", viewName);
        verify(model, times(1)).addAttribute("books", books);
        verify(model, times(1)).addAttribute("query", query);
    }

    @Test
    public void testSearch_WithoutQuery() {
        String viewName = studentController.search(null, model);

        assertEquals("search", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }
}
