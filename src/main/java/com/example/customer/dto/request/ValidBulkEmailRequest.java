package com.example.customer.dto.request;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BulkEmailRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBulkEmailRequest {

    String message() default "Email request is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
