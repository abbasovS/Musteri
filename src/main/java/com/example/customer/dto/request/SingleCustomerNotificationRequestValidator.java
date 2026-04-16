package com.example.customer.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class SingleCustomerNotificationRequestValidator
        implements ConstraintValidator<ValidSingleCustomerNotificationRequest, SingleCustomerNotificationRequest> {

    @Override
    public boolean isValid(SingleCustomerNotificationRequest value, ConstraintValidatorContext context) {
        if (value == null || value.getChannel() == null) {
            return true;
        }

        boolean valid;
        switch (value.getChannel()) {
            case PUSH -> valid =
                    StringUtils.hasText(value.getTitle()) &&
                            StringUtils.hasText(value.getMessage());

            case SMS -> valid =
                    StringUtils.hasText(value.getMessage());

            case EMAIL -> valid =
                    StringUtils.hasText(value.getSubject()) &&
                            (StringUtils.hasText(value.getMessageHtml()) ||
                                    StringUtils.hasText(value.getMessageText()));

            default -> valid = false;
        }

        if (valid) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        switch (value.getChannel()) {
            case PUSH -> context.buildConstraintViolationWithTemplate(
                    "For PUSH, title and message are required"
            ).addConstraintViolation();

            case SMS -> context.buildConstraintViolationWithTemplate(
                    "For SMS, message is required"
            ).addConstraintViolation();

            case EMAIL -> context.buildConstraintViolationWithTemplate(
                    "For EMAIL, subject is required and at least one of messageHtml or messageText is required"
            ).addConstraintViolation();

            default -> context.buildConstraintViolationWithTemplate(
                    "Unsupported notification channel"
            ).addConstraintViolation();
        }

        return false;
    }
}