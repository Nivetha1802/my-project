package com.library.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.controller.UserController;
import com.library.entity.UserEntity;
import com.library.model.LoginUser;
import com.library.model.User;
import com.library.service.UserService;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogout() {
        String viewName = userController.returnToHomePage();
        assertEquals("login", viewName);
    }

    @Test
    public void testGetHomePage_Student() {
        String viewName = userController.getHomePage("student");
        assertEquals("studentHomePage", viewName);
    }

    @Test
    public void testGetHomePage_Teacher() {
        String viewName = userController.getHomePage("teacher");
        assertEquals("studentHomePage", viewName);
    }

    @Test
    public void testGetHomePage_Librarian() {
        String viewName = userController.getHomePage("librarian");
        assertEquals("librarianHomePage", viewName);
    }

    @Test
    public void testGetStudentHomePage() {
        String viewName = userController.getStudentHomePage();
        assertEquals("studentHomePage", viewName);
    }

    @Test
    public void testGetLibrarianHomePage() {
        String viewName = userController.getLibrarianHomePage();
        assertEquals("librarianHomePage", viewName);
    }

    @Test
    public void testShowSignupPage() {
        User user = new User();
        String viewName = userController.showSignupPage(user);
        assertEquals("signup", viewName);
    }

    @Test
    public void testSubmitRegistrationFormDetails_WithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        User user = new User();
        String viewName = userController.submitRegistrationFormDetails(user, bindingResult, redirectAttributes,
                session);

        assertEquals("signup", viewName);
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void testSubmitRegistrationFormDetails_WithoutErrors() {
        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();
        user.setId(1);
        user.setRole("student");

        String viewName = userController.submitRegistrationFormDetails(user, bindingResult, redirectAttributes,
                session);

        assertEquals("redirect:/home?role=student", viewName);
        verify(userService, times(1)).saveUser(user);
        verify(session, times(1)).setAttribute("userId", 1);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Registration successful!");
    }

    @Test
    public void testShowLoginPage() {
        LoginUser loginUser = new LoginUser();
        String viewName = userController.showLoginPage(loginUser);
        assertEquals("login", viewName);
    }

    @Test
    public void testSubmitLoginFormDetails_WithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        LoginUser loginUser = new LoginUser();
        String viewName = userController.submitLoginFormDetails(loginUser, bindingResult, redirectAttributes, session);

        assertEquals("login", viewName);
        verify(userService, never()).authenticate(anyInt(), anyString());
    }

    @Test
    public void testSubmitLoginFormDetails_WithoutErrors_InvalidCredentials() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.authenticate(anyInt(), anyString())).thenReturn(Optional.empty()); // Change to
                                                                                            // Optional.empty()

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("password");

        String viewName = userController.submitLoginFormDetails(loginUser, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/login", viewName);
        verify(userService, times(1)).authenticate(1, "password");
        verify(redirectAttributes, times(1)).addFlashAttribute("error", "Invalid credentials!");
    }

    @Test
    public void testSubmitLoginFormDetails_WithoutErrors_ValidCredentials() {
        when(bindingResult.hasErrors()).thenReturn(false);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setRole("student");
        Optional<UserEntity> optionalBook = Optional.of(userEntity);
        when(userService.authenticate(anyInt(), anyString())).thenReturn(optionalBook);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("password");

        String viewName = userController.submitLoginFormDetails(loginUser, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/home?role=student", viewName);
        verify(userService, times(1)).authenticate(1, "password");
        verify(session, times(1)).setAttribute("userId", 1);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Login successful!");
    }
}
