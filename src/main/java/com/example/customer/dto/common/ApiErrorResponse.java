package com.example.customer.dto.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private boolean success;
    private String message;
    private List<FieldErrorDto> errors;

    public static ApiErrorResponse of(String message, List<FieldErrorDto> errors) {
        return ApiErrorResponse.builder()
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}
