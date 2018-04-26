package com.salvador.login.validation;

import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateEmailImpl implements ConstraintValidator<ValidateEmail, String> {
    @Override
    public void initialize(ValidateEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (EmailValidator.getInstance().isValid(username)) {
            return true;
        }
        return false;
    }
}
