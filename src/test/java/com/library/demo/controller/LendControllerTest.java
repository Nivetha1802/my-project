package com.library.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.Dto.GoogleBooks;
import com.library.controller.LendController;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.service.GoogleBooksServiceImpl;
import com.library.service.LendDetailsServiceImpl;
import com.library.service.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LendControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private LendDetailsServiceImpl lendDetailsService;

    @Mock
    private GoogleBooksServiceImpl googleBooksService;

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
    private LendController lendController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowLendtablePage_WithQuery() {
        String query = "test";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(googleBooksService.searchBook(query)).thenReturn(books);

        String viewName = lendController.showLendtablePage(query, model);

        assertEquals("lendTablePage", viewName);
        verify(model, times(1)).addAttribute("books", books);
        verify(model, times(1)).addAttribute("query", query);
    }

    @Test
    public void testShowLendtablePage_WithoutQuery() {
        String viewName = lendController.showLendtablePage(null, model);

        assertEquals("lendTablePage", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitLendTable() throws JsonProcessingException {
        String selectedBooks = "[{\"id\":\"HT0HEAAAQBAJ\",\"title\":\"The Archer\",\"authors\":\"Paulo Coelho\",\"publisher\":\"Penguin Random House India Private Limited\",\"publishedDate\":\"2020-11-16\"}]";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        doReturn(books).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        String viewName = lendController.submitLendTable(selectedBooks, session);

        assertEquals("redirect:/lendDetails", viewName);
        verify(session, times(1)).setAttribute("selectedBooks", books);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitLendTable_EmptySelectedBooks() throws JsonProcessingException {
        String selectedBooks = "";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        doReturn(books).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        String viewName = lendController.submitLendTable(selectedBooks, session);

        assertEquals("redirect:/lendtable", viewName);
    }

    @Test
    public void testShowLendDetails() {
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(session.getAttribute("selectedBooks")).thenReturn(books);

        String viewName = lendController.showLendDetails(session, model);

        assertEquals("lendDetailsPage", viewName);
        verify(model, times(1)).addAttribute("selectedBooks", books);
    }

    @Test
    public void testShowLendDetails_SelectedBooksNull() {
        // Arrange
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        when(session.getAttribute("selectedBooks")).thenReturn(null);
        // Act
        String viewName = lendController.showLendDetails(session, model);
        // Assert
        assertEquals("lendDetailsPage", viewName);
        verify(model, never()).addAttribute("selectedBooks", null);
    }


    @SuppressWarnings("unchecked")
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
        String viewName = lendController.create(null, null, redirectAttributes, null, selectedBooks, session);

        // Assert
        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).processBookLendDetails(any(LendDetails.class), eq(optionalUser));
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Lent Books!");
    }

    @Test
    public void testSubmitLendDetail_UserNotLoggedIn() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        String selectedBooksJson = "[{\"id\":1}, {\"id\":2}]";
        List<LendDetails> selectedBooksList = List.of(new LendDetails(1, "Book1", "Author1", null, null),
                new LendDetails(2, "Book2", "Author2", null, null));

        when(objectMapper.readValue(selectedBooksJson, new TypeReference<List<LendDetails>>() {
        }))
                .thenReturn(selectedBooksList);
        when(session.getAttribute("userId")).thenReturn(null);

        // Act
        String viewName = lendController.create(new LendDetails(), bindingResult, redirectAttributes, model,
                selectedBooksJson, session);

        // Assert
        assertEquals("redirect:/login", viewName);
        assertEquals("User not logged in!", redirectAttributes.getFlashAttributes().get("error"));
        verify(lendDetailsService, never()).processBookLendDetails(any(), any());
    }

    @Test
    public void testSubmitLendDetails_SelectedBooksNullOrEmpty() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute("userId")).thenReturn(1);
        // Act
        String viewName = lendController.create(new LendDetails(), bindingResult, redirectAttributes, model, null,
                session);
        // Assert
        assertEquals("redirect:/lendDetails", viewName);
        verify(lendDetailsService, never()).processBookLendDetails(any(), any());
    }
    @Test
public void testSubmitLendDetail_UserNotFound() throws Exception {
    // Arrange
    BindingResult bindingResult = mock(BindingResult.class);
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
    Model model = mock(Model.class);
    HttpSession session = mock(HttpSession.class);

    String selectedBooksJson = "[{\"id\":1}, {\"id\":2}]";
    List<LendDetails> selectedBooksList = List.of(new LendDetails(1, "Book1", "Author1", null, null),
                                                  new LendDetails(2, "Book2", "Author2", null, null));

    when(objectMapper.readValue(selectedBooksJson, new TypeReference<List<LendDetails>>() {}))
        .thenReturn(selectedBooksList);
    when(session.getAttribute("userId")).thenReturn(1);
    when(userService.getById(1)).thenReturn(Optional.empty());

    // Act
    String viewName = lendController.create(new LendDetails(), bindingResult, redirectAttributes, model, selectedBooksJson, session);

    // Assert
    assertEquals("redirect:/login", viewName);
    assertEquals("User not found!", redirectAttributes.getFlashAttributes().get("error"));
    verify(lendDetailsService, never()).processBookLendDetails(any(), any());
}


    @Test
    public void testShowReturnBooksPage() {
        Integer userId = 1;
        List<LendDetails> lendBooks = Collections.singletonList(new LendDetails());
        when(session.getAttribute("userId")).thenReturn(userId);
        when(lendDetailsService.getLendDetailsByUserId(userId)).thenReturn(lendBooks);

        String viewName = lendController.showReturnBooksPage(model, session);

        assertEquals("returnPage", viewName);
        verify(model, times(1)).addAttribute("lendBooks", lendBooks);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubmitReturnBooks() throws Exception {
        String selectedBooks = "[{\"lendId\":1,\"bookname\":\"Test Book\"}]";
        LendDetails lendDetails = new LendDetails();
        lendDetails.setId(1);
        List<LendDetails> lendDetailsList = Collections.singletonList(lendDetails);

        doReturn(lendDetailsList).when(objectMapper).readValue(eq(selectedBooks), any(TypeReference.class));

        String viewName = lendController.delete(null, null, redirectAttributes, null, selectedBooks, session);

        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).delete(eq(1));
        verify(session, times(1)).removeAttribute("selectedBooks");
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Successfully Returned Books!");
    }

    @Test
    public void testSubmitReturnBooks_SelectedBooksNullOrEmpty() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);
        String selectedBooks = "";
        // Act
        String viewName = lendController.delete(null, bindingResult, redirectAttributes, model, selectedBooks, session);
        // Assert
        assertEquals("redirect:/returntable", viewName);
    }

    @Test
    public void testShowRenewtablePage() {
        Integer userId = 1;
        List<LendDetails> lendBooks = Collections.singletonList(new LendDetails());
        when(session.getAttribute("userId")).thenReturn(userId);
        when(lendDetailsService.getLendDetailsByUserId(userId)).thenReturn(lendBooks);
        String viewName = lendController.showRenewtablePage(model, session);
        assertEquals("renewPage", viewName);
        verify(model, times(1)).addAttribute("lendBooks", lendBooks);
    }

    @Test
    public void testUpdate_SelectedBooksNullOrEmpty() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);
        String selectedBooks = "";
        // Act
        String viewName = lendController.update(null, bindingResult, redirectAttributes, model, selectedBooks);
        // Assert
        assertEquals("redirect:/renewtable", viewName);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdate_SuccessfulRenew() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = mock(Model.class);
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        LendDetailsServiceImpl lendDetailsService = mock(LendDetailsServiceImpl.class);
        LendController lendController = new LendController(null, lendDetailsService, null, objectMapper);
        String selectedBooksJson = "[{\"id\":1,\"bookname\":\"Book1\",\"author\":\"Author1\"},{\"id\":2,\"bookname\":\"Book2\",\"author\":\"Author2\"}]";
        List<LendDetails> selectedBooksList = List.of(
                new LendDetails(1, "Book1", "Author1", null, null),
                new LendDetails(2, "Book2", "Author2", null, null));
        when(objectMapper.readValue(eq(selectedBooksJson), any(TypeReference.class)))
                .thenReturn(selectedBooksList);
        // Act
        String viewName = lendController.update(null, bindingResult, redirectAttributes, model, selectedBooksJson);
        // Assert
        assertEquals("redirect:/studentHomePage", viewName);
        verify(lendDetailsService, times(1)).renewBook(1);
        verify(lendDetailsService, times(1)).renewBook(2);
        assertEquals("Successfully Renewed Books!", redirectAttributes.getFlashAttributes().get("message"));
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

        String viewName = lendController.showFineDetailsPage(model, session);

        assertEquals("finePage", viewName);
        verify(model, times(1)).addAttribute("books", lendBooksWithFine);
        verify(model, times(1)).addAttribute("user", "Test User");
    }

    @Test
    public void testShowFineDetailsPage_NoUserId() {

        when(session.getAttribute("userId")).thenReturn(null);

        String viewName = lendController.showFineDetailsPage(model, session);

        assertEquals("finePage", viewName);

    }

    @Test
    public void testShowFineDetailsPage_NoUserFound() {
        Integer userId = 1;
        when(session.getAttribute("userId")).thenReturn(userId);
        when(userService.getById(userId)).thenReturn(Optional.empty());
        String viewName = lendController.showFineDetailsPage(model, session);

        assertEquals("error", viewName);
        verify(model, times(1)).addAttribute("error", "User not found!");

    }

    @Test
    public void testSearch_WithQuery() {
        String query = "test";
        List<GoogleBooks> books = Collections.singletonList(new GoogleBooks());
        when(googleBooksService.searchBook(query)).thenReturn(books);

        String viewName = lendController.search(query, model);

        assertEquals("search", viewName);
        verify(model, times(1)).addAttribute("books", books);
        verify(model, times(1)).addAttribute("query", query);
    }

    @Test
    public void testSearch_WithoutQuery() {
        String viewName = lendController.search(null, model);

        assertEquals("search", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }
}