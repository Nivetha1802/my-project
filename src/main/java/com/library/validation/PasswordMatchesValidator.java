package com.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.library.Dto.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto user, ConstraintValidatorContext context) {
        if (user == null || user.getPassword() == null || user.getConfirmPassword() == null) {
            return false;
        }
        return user.getPassword().equals(user.getConfirmPassword());
    }
}

