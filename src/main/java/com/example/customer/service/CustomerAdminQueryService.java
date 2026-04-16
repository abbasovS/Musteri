package com.example.customer.service;



import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.request.CustomerListRequest;
import com.example.customer.dto.request.CustomerStatsRequest;
import com.example.customer.dto.response.CustomerListItemResponse;
import com.example.customer.dto.response.CustomerProfileResponse;
import com.example.customer.dto.response.CustomerStatsResponse;
import com.example.customer.dto.response.CustomerSubscriptionsResponse;

import java.util.UUID;

public interface CustomerAdminQueryService {

    PageResponse<CustomerListItemResponse> getCustomers(CustomerListRequest request);

    CustomerStatsResponse getStats(CustomerStatsRequest request, CustomerListRequest currentFilters);

    CustomerProfileResponse getCustomerProfile(UUID customerId);

    CustomerSubscriptionsResponse getCustomerSubscriptions(UUID customerId);
}