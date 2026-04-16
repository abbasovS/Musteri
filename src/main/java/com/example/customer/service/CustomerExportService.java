package com.example.customer.service;



import com.example.customer.dto.request.CustomerExportRequest;
import com.example.customer.dto.response.ExportJobAcceptedResponse;
import com.example.customer.dto.response.ExportJobStatusResponse;

import java.util.UUID;

public interface CustomerExportService {

    ExportJobAcceptedResponse createExportJob(CustomerExportRequest request, UUID adminId);

    ExportJobStatusResponse getExportJobStatus(UUID exportJobId);
}