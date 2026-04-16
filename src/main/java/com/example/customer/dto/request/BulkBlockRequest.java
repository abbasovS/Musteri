package com.example.customer.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BulkBlockRequest {

    private List<UUID> customerIds;

    private Boolean selectAllFiltered = false;

    private BulkTargetFilterRequest filters;

    @NotBlank
    @Size(max = 500)
    private String reason;
}