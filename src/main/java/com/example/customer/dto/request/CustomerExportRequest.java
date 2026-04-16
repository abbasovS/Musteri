package com.example.customer.dto.request;


import com.example.customer.enums.ExportFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomerExportRequest {

    private List<UUID> customerIds;

    private Boolean selectAllFiltered = false;

    @Valid
    private BulkTargetFilterRequest filters;

    @NotNull
    private ExportFormat format;

    @NotEmpty
    private List<String> columns;
}