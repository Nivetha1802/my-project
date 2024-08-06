package com.library.demo.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.validation.IntegerValidator;

import javax.validation.ConstraintValidatorContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IntegerValidatorTest {

    private IntegerValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new IntegerValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testValidInteger() {
        assertTrue(validator.isValid(123, context));
    }

    @Test
    public void testNullValue() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    public void testInvalidInteger() {
        assertTrue(validator.isValid(Integer.valueOf(0), context));
    }
}
