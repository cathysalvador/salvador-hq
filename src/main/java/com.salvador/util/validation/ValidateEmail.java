package com.salvador.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=ValidateEmailImpl.class)
@Documented
public @interface ValidateEmail {

    String message() default "Invalid email";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload() default {};
}
