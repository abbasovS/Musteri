package com.example.customer.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ValidBulkEmailRequest
public class BulkEmailRequest {

    private List<UUID> customerIds;

    private Boolean selectAllFiltered = false;

    @Valid
    private BulkTargetFilterRequest filters;

    @Size(max = 255)
    private String subject;

    @Size(max = 20000)
    private String messageHtml;

    @Size(max = 10000)
    private String messageText;
}