package com.example.customer.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class BulkEmailRequestValidator implements ConstraintValidator<ValidBulkEmailRequest, BulkEmailRequest> {

    @Override
    public boolean isValid(BulkEmailRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean hasSubject = StringUtils.hasText(value.getSubject());
        boolean hasHtml = StringUtils.hasText(value.getMessageHtml());
        boolean hasText = StringUtils.hasText(value.getMessageText());

        boolean valid = hasSubject && (hasHtml || hasText);

        if (valid) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "For EMAIL, subject is required and at least one of messageHtml or messageText must be provided"
        ).addConstraintViolation();

        return false;
    }
}