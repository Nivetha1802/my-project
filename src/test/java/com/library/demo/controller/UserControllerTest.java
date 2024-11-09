package com.library.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.library.Dto.LoginUser;
import com.library.Dto.UserDto;
import com.library.controller.UserController;
import com.library.entity.UserEntity;
import com.library.service.UserServiceImpl;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

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
        String viewName = userController.showLoginPage(null);
        assertEquals("loginPage", viewName);
    }

    @Test
    public void testGetStudentHomePage() {
        String viewName = userController.getStudentHomePage();
        assertEquals("studentHome", viewName);
    }

    @Test
    public void testGetLibrarianHomePage() {
        String viewName = userController.getLibrarianHomePage();
        assertEquals("librarianHome", viewName);
    }

    @Test
    public void testShowSignupPage() {
        String viewName = userController.showSignupPage(model);
        assertEquals("signupPage", viewName);
    }

    @Test
    public void testSubmitRegistrationFormDetails_WithErrors() throws JsonMappingException, JsonProcessingException {
        when(bindingResult.hasErrors()).thenReturn(true);

        UserDto user = new UserDto();
        String viewName = userController.create(user, bindingResult, redirectAttributes,
                model, null, session);

        assertEquals("signupPage", viewName);
        verify(userService, never()).saveUser(any(UserDto.class));
    }

    @Test
    public void testSubmitRegistrationFormDetails_WithoutErrors() throws JsonMappingException, JsonProcessingException {
        when(bindingResult.hasErrors()).thenReturn(false);

        UserDto user = new UserDto();
        user.setId(1);
        user.setRole("student");

        String viewName = userController.create(user, bindingResult, redirectAttributes, null, null, session);

        assertEquals("studentHomePage", viewName);
        verify(userService, times(1)).saveUser(user);
        verify(session, times(1)).setAttribute("userId", 1);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Registration successful!");
    }

    @Test
    public void testShowLoginPage() {
        LoginUser loginUser = new LoginUser();
        String viewName = userController.showLoginPage(loginUser);
        assertEquals("loginPage", viewName);
    }

    @Test
    public void testSubmitLoginFormDetails_WithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        LoginUser loginUser = new LoginUser();
        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

        assertEquals("loginPage", viewName);
        verify(userService, never()).authenticate(anyInt(), anyString());
    }

    @Test
    public void testSubmitLoginFormDetails_WithoutErrors_InvalidCredentials() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.authenticate(anyInt(), anyString())).thenReturn(Optional.empty());

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("password");

        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

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

        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

        assertEquals("studentHomePage", viewName);
        verify(userService, times(1)).authenticate(1, "password");
        verify(session, times(1)).setAttribute("userId", 1);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Login successful!");
    }
    @Test
    public void testSubmitLogin_SuccessfulLogin_StudentRole() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("password");

        UserEntity user = new UserEntity();
        user.setId(1);
        user.setRole("student");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.authenticate(loginUser.getId(), loginUser.getPassword())).thenReturn(Optional.of(user));

        // Act
        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

        // Assert
        assertEquals("studentHomePage", viewName);
        verify(session).setAttribute("userId", user.getId());
        assertEquals("Login successful!", redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testSubmitLogin_SuccessfulLogin_LibrarianRole() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("password");

        UserEntity user = new UserEntity();
        user.setId(1);
        user.setRole("librarian");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.authenticate(loginUser.getId(), loginUser.getPassword())).thenReturn(Optional.of(user));

        // Act
        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

        // Assert
        assertEquals("librarianHomePage", viewName);
        verify(session).setAttribute("userId", user.getId());
        assertEquals("Login successful!", redirectAttributes.getFlashAttributes().get("message"));
    }

    @Test
    public void testSubmitLogin_InvalidCredentials() throws Exception {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        HttpSession session = mock(HttpSession.class);

        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        loginUser.setPassword("wrongPassword");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.authenticate(loginUser.getId(), loginUser.getPassword())).thenReturn(Optional.empty());

        // Act
        String viewName = userController.get(loginUser, bindingResult, redirectAttributes, session);

        // Assert
        assertEquals("redirect:/login", viewName);
        assertEquals("Invalid credentials!", redirectAttributes.getFlashAttributes().get("error"));
    }

}
