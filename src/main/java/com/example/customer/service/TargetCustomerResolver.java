package com.example.customer.service;



import com.example.customer.dto.request.BulkTargetFilterRequest;

import java.util.List;
import java.util.UUID;

public interface TargetCustomerResolver {

    List<UUID> resolveTargets(List<UUID> customerIds, Boolean selectAllFiltered, BulkTargetFilterRequest filters);
}
