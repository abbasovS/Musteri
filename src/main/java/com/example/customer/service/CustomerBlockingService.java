package com.example.customer.service;



import com.example.customer.dto.request.BulkBlockRequest;
import com.example.customer.dto.request.SingleBlockRequest;
import com.example.customer.dto.response.BulkBlockResponse;
import com.example.customer.dto.response.CustomerBlockHistoryResponse;

import java.util.UUID;

public interface CustomerBlockingService {

    BulkBlockResponse bulkBlock(BulkBlockRequest request, UUID adminId);

    void blockSingle(UUID customerId, SingleBlockRequest request, UUID adminId);

    CustomerBlockHistoryResponse getBlockHistory(UUID customerId);
}