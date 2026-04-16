package com.example.customer.dto.request;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SingleCustomerNotificationRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSingleCustomerNotificationRequest {

    String message() default "Single customer notification request is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
