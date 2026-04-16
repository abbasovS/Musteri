package com.example.customer.service.port;


import com.example.customer.domain.CustomerReadModel;
import com.example.customer.dto.request.BulkTargetFilterRequest;
import com.example.customer.dto.request.CustomerListRequest;
import com.example.customer.dto.response.CustomerStatsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerAdminReadPort {

    Page<CustomerReadModel> findCustomers(CustomerListRequest request);

    CustomerStatsResponse getStats(boolean applyCurrentFilters, BulkTargetFilterRequest filters);

    List<UUID> findCustomerIdsByFilters(BulkTargetFilterRequest filters);

    Optional<CustomerReadModel> findCustomerById(UUID customerId);
}