package com.example.customer.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BulkPushNotificationRequest {

    private List<UUID> customerIds;

    private Boolean selectAllFiltered = false;

    @Valid
    private BulkTargetFilterRequest filters;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String message;

    @Size(max = 500)
    private String deepLink;
}