package com.example.customer.service.impl;


import com.example.customer.domain.ExportJobEntity;
import com.example.customer.dto.request.CustomerExportRequest;
import com.example.customer.dto.response.ExportJobAcceptedResponse;
import com.example.customer.dto.response.ExportJobStatusResponse;
import com.example.customer.enums.ExportJobStatus;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.exception.ValidationException;
import com.example.customer.repository.ExportJobRepository;
import com.example.customer.service.CustomerExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerExportServiceImpl implements CustomerExportService {

    private static final Set<String> ALLOWED_COLUMNS = Set.of(
            "publicId",
            "fullName",
            "phoneNumber",
            "email",
            "accountStatus",
            "subscriptionStatus",
            "packageCode"
    );

    private final ExportJobRepository exportJobRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ExportJobAcceptedResponse createExportJob(CustomerExportRequest request, UUID adminId) {
        validateColumns(request.getColumns());

        ExportJobEntity entity = new ExportJobEntity();
        entity.setRequestedBy(adminId);
        entity.setSelectAllFiltered(Boolean.TRUE.equals(request.getSelectAllFiltered()));
        entity.setFormat(request.getFormat());
        entity.setStatus(ExportJobStatus.PROCESSING);
        entity.setFiltersJson(writeJson(request.getFilters()));
        entity.setCustomerIdsJson(writeJson(request.getCustomerIds()));

        exportJobRepository.save(entity);

        return ExportJobAcceptedResponse.builder()
                .exportJobId(entity.getId().toString())
                .status(entity.getStatus().name())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ExportJobStatusResponse getExportJobStatus(UUID exportJobId) {
        ExportJobEntity entity = exportJobRepository.findById(exportJobId)
                .orElseThrow(() -> new ResourceNotFoundException("Export job not found: " + exportJobId));

        return ExportJobStatusResponse.builder()
                .exportJobId(entity.getId().toString())
                .status(entity.getStatus().name())
                .fileUrl(entity.getFileUrl())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    private void validateColumns(List<String> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new ValidationException("columns must not be empty");
        }

        boolean allAllowed = columns.stream().allMatch(ALLOWED_COLUMNS::contains);
        if (!allAllowed) {
            throw new ValidationException("Export columns contain unsupported values");
        }
    }

    private String writeJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch ( Exception e) {
            throw new ValidationException("Failed to serialize export request payload");
        }
    }
}
