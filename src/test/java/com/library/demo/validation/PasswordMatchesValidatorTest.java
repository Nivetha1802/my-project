package com.library.demo.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.model.User;
import com.library.validation.PasswordMatchesValidator;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordMatchesValidatorTest {

    private PasswordMatchesValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new PasswordMatchesValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testPasswordsMatch() {
        User user = new User();
        user.setPassword("password123");
        user.setConfirmPassword("password123");
        assertTrue(validator.isValid(user, context));
    }

    @Test
    public void testPasswordsDoNotMatch() {
        User user = new User();
        user.setPassword("password123");
        user.setConfirmPassword("differentPassword");
        assertFalse(validator.isValid(user, context));
    }

    @Test
    public void testNullPassword() {
        User user = new User();
        user.setPassword(null);
        user.setConfirmPassword("password123");
        assertFalse(validator.isValid(user, context));
    }

    @Test
    public void testNullConfirmPassword() {
        User user = new User();
        user.setPassword("password123");
        user.setConfirmPassword(null);
        assertFalse(validator.isValid(user, context));
    }

    @Test
    public void testBothNullPasswords() {
        User user = new User();
        user.setPassword(null);
        user.setConfirmPassword(null);
        assertFalse(validator.isValid(user, context));
    }
}
