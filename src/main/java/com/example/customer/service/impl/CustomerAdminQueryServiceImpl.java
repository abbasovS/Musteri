package com.example.customer.service.impl;

import com.example.customer.domain.CustomerReadModel;
import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;
import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.request.BulkTargetFilterRequest;
import com.example.customer.dto.request.CustomerListRequest;
import com.example.customer.dto.request.CustomerStatsRequest;
import com.example.customer.dto.response.CustomerListItemResponse;
import com.example.customer.dto.response.CustomerProfileResponse;
import com.example.customer.dto.response.CustomerStatsResponse;
import com.example.customer.dto.response.CustomerSubscriptionsResponse;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.service.*;
import com.example.customer.service.port.CustomerAdminReadPort;
import com.example.customer.service.port.CustomerSubscriptionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomerAdminQueryServiceImpl implements CustomerAdminQueryService {

    private final CustomerAdminReadPort customerAdminReadPort;
    private final CustomerSubscriptionPort customerSubscriptionPort;
    private final CustomerProfileService customerProfileService;
    private final CustomerSubscriptionReadService customerSubscriptionReadService;

    @Override
    public PageResponse<CustomerListItemResponse> getCustomers(CustomerListRequest request) {
        Page<CustomerReadModel> page = customerAdminReadPort.findCustomers(request);

        List<UUID> ids = page.getContent().stream()
                .map(CustomerReadModel::getId)
                .toList();

        Map<UUID, CustomerSubscriptionSnapshotReadModel> subscriptionMap =
                customerSubscriptionPort.getCurrentSubscriptionsForCustomers(ids);

        List<CustomerListItemResponse> items = page.getContent().stream()
                .map(customer -> CustomerMapper.toListItem(
                        customer,
                        subscriptionMap.get(customer.getId())
                ))
                .toList();

        return PageResponse.<CustomerListItemResponse>builder()
                .items(items)
                .page(request.getPage())
                .size(request.getSize())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    public CustomerStatsResponse getStats(CustomerStatsRequest request, CustomerListRequest currentFilters) {
        BulkTargetFilterRequest filters = null;

        if (Boolean.TRUE.equals(request.getApplyCurrentFilters()) && currentFilters != null) {
            filters = new BulkTargetFilterRequest();
            filters.setSearch(currentFilters.getSearch());
            filters.setPackageCodes(currentFilters.getPackageCodes());
            filters.setDurationMonths(currentFilters.getDurationMonths());
            filters.setSubscriptionStatuses(currentFilters.getSubscriptionStatuses());
            filters.setAccountStatuses(currentFilters.getAccountStatuses());
        }

        return customerAdminReadPort.getStats(Boolean.TRUE.equals(request.getApplyCurrentFilters()), filters);
    }

    @Override
    public CustomerProfileResponse getCustomerProfile(UUID customerId) {
        return customerProfileService.getProfile(customerId);
    }

    @Override
    public CustomerSubscriptionsResponse getCustomerSubscriptions(UUID customerId) {
        return customerSubscriptionReadService.getSubscriptions(customerId);
    }
}
