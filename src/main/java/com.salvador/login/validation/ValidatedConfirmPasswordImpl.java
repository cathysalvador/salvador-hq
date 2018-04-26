package com.salvador.login.validation;

import com.salvador.login.dto.UserPasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatedConfirmPasswordImpl implements
        ConstraintValidator<ValidateConfirmPassword, Object> {

    @Override
    public void initialize(ValidateConfirmPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserPasswordDto userPasswordDto = (UserPasswordDto) obj;
        return userPasswordDto.getPassword().equals(userPasswordDto.getConfirmPassword());
    }
}
