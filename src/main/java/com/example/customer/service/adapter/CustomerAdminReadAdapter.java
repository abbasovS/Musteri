package com.example.customer.service.adapter;


import com.example.customer.domain.CustomerReadModel;
import com.example.customer.dto.request.BulkTargetFilterRequest;
import com.example.customer.dto.request.CustomerListRequest;
import com.example.customer.dto.response.CustomerStatsResponse;
import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.SubscriptionStatus;
import com.example.customer.repository.CustomerReadModelRepository;
import com.example.customer.repository.CustomerSubscriptionSnapshotReadModelRepository;
import com.example.customer.service.port.CustomerAdminReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAdminReadAdapter implements CustomerAdminReadPort {

    private final CustomerReadModelRepository customerReadModelRepository;
    private final CustomerSubscriptionSnapshotReadModelRepository subscriptionSnapshotRepository;

    @Override
    public Page<CustomerReadModel> findCustomers(CustomerListRequest request) {
        Pageable pageable = PageRequest.of(
                Math.max(request.getPage() - 1, 0),
                request.getSize(),
                resolveSort(request)
        );

        if (request.getAccountStatuses() != null && !request.getAccountStatuses().isEmpty()) {
            return customerReadModelRepository.findByAccountStatusIn(request.getAccountStatuses(), pageable);
        }

        return customerReadModelRepository.findAll(pageable);
    }

    @Override
    public CustomerStatsResponse getStats(boolean applyCurrentFilters, BulkTargetFilterRequest filters) {
        // MVP: əvvəl ümumi sistem statistikası qaytarırıq.
        // Filtered stats-i sonrakı step-də dəqiqləşdirəcəyik.
        long totalCustomers = customerReadModelRepository.count();
        long activeSubscriptions = subscriptionSnapshotRepository.countBySubscriptionStatus(SubscriptionStatus.ACTIVE);
        long expiredSubscriptions = subscriptionSnapshotRepository.countBySubscriptionStatus(SubscriptionStatus.EXPIRED);
        long endingIn7Days = subscriptionSnapshotRepository.countByEndDateBetween(LocalDate.now(), LocalDate.now().plusDays(7));

        return CustomerStatsResponse.builder()
                .totalCustomers(totalCustomers)
                .activeSubscriptions(activeSubscriptions)
                .expiredSubscriptions(expiredSubscriptions)
                .endingIn7Days(endingIn7Days)
                .build();
    }

    @Override
    public List<UUID> findCustomerIdsByFilters(BulkTargetFilterRequest filters) {
        // MVP: filter tətbiqi növbəti step-də genişlənəcək.
        // Hələlik account status varsa ona görə, yoxdursa hamısını qaytarırıq.
        if (filters != null && filters.getAccountStatuses() != null && !filters.getAccountStatuses().isEmpty()) {
            return customerReadModelRepository.findByAccountStatusIn(
                            filters.getAccountStatuses(),
                            Pageable.unpaged()
                    )
                    .stream()
                    .map(CustomerReadModel::getId)
                    .toList();
        }

        return customerReadModelRepository.findAll()
                .stream()
                .map(CustomerReadModel::getId)
                .toList();
    }

    @Override
    public Optional<CustomerReadModel> findCustomerById(UUID customerId) {
        return customerReadModelRepository.findById(customerId);
    }

    private Sort resolveSort(CustomerListRequest request) {
        String sortBy = request.getSortBy();
        Sort.Direction direction = request.getSortOrder() != null ? request.getSortOrder() : Sort.Direction.DESC;

        String mappedField = switch (sortBy == null ? "createdAt" : sortBy) {
            case "fullName" -> "fullName";
            case "registrationDate" -> "registrationDate";
            case "subscriptionEndDate" -> "createdAt"; // real join sort-u sonrakı mərhələdə
            case "createdAt" -> "createdAt";
            default -> "createdAt";
        };

        return Sort.by(direction, mappedField);
    }
}