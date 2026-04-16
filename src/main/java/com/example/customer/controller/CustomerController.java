package com.example.customer.controller;


import com.example.customer.dto.common.ApiResponse;
import com.example.customer.dto.request.CheckinHistoryRequest;
import com.example.customer.dto.request.CustomerListRequest;
import com.example.customer.dto.response.CustomerListResponse;
import com.example.customer.dto.response.CustomerProfileResponse;
import com.example.customer.dto.response.CustomerStatsResponse;
import com.example.customer.service.CustomerAdminQueryService;
import com.example.customer.service.CustomerProfileService;
import com.example.customer.service.CustomerSubscriptionReadService;
import com.example.customer.service.ReferenceFilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/customers")
public class CustomerController {

    private final CustomerAdminQueryService customerAdminQueryService;
    private final CustomerProfileService customerProfileService;
    private final CustomerSubscriptionReadService customerSubscriptionReadService;
    private final ReferenceFilterService referenceFilterService;

    @GetMapping
    public ApiResponse<CustomerListResponse> getCustomers(@Valid @ModelAttribute CustomerListRequest request) {
        return ApiResponse.success(customerAdminQueryService.getCustomers(request));
    }

    @GetMapping("/stats")
    public ApiResponse<CustomerStatsResponse> getCustomerStats(
            @RequestParam(defaultValue = "false") boolean applyCurrentFilters,
            @Valid @ModelAttribute CustomerListRequest request
    ) {
        return ApiResponse.success(customerAdminQueryService.getCustomerStats(applyCurrentFilters, request));
    }

    @GetMapping("/filter-options")
    public ApiResponse<CustomerFilterOptionsResponse> getFilterOptions() {
        return ApiResponse.success(referenceFilterService.getFilterOptions());
    }

    @GetMapping("/{customerId}/profile")
    public ApiResponse<CustomerProfileResponse> getCustomerProfile(@PathVariable UUID customerId) {
        return ApiResponse.success(customerProfileService.getCustomerProfile(customerId));
    }

    @GetMapping("/{customerId}/subscriptions")
    public ApiResponse<List<CustomerSubscriptionResponse>> getCustomerSubscriptions(@PathVariable UUID customerId) {
        return ApiResponse.success(customerSubscriptionReadService.getSubscriptions(customerId));
    }

    @GetMapping("/{customerId}/payments")
    public ApiResponse<Object> getCustomerPayments(@PathVariable UUID customerId) {
        // Step 1 üçün placeholder
        return ApiResponse.success(null);
    }

    @GetMapping("/{customerId}/checkins")
    public ApiResponse<Object> getCustomerCheckins(
            @PathVariable UUID customerId,
            @Valid @ModelAttribute CheckinHistoryRequest request
    ) {
        // Step 1 üçün placeholder
        return ApiResponse.success(null);
    }
}
