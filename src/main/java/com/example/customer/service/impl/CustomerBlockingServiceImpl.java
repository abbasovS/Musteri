package com.example.customer.service.impl;

import com.example.customer.domain.CustomerBlockRecordEntity;
import com.example.customer.dto.request.BulkBlockRequest;
import com.example.customer.dto.request.SingleBlockRequest;
import com.example.customer.dto.response.BulkBlockResponse;
import com.example.customer.dto.response.CustomerBlockHistoryItemResponse;
import com.example.customer.dto.response.CustomerBlockHistoryResponse;
import com.example.customer.repository.CustomerBlockRecordRepository;
import com.example.customer.service.port.CustomerAccountCommandPort;
import com.example.customer.service.CustomerBlockingService;
import com.example.customer.service.TargetCustomerResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerBlockingServiceImpl implements CustomerBlockingService {

    private final CustomerBlockRecordRepository customerBlockRecordRepository;
    private final CustomerAccountCommandPort customerAccountCommandPort;
    private final TargetCustomerResolver targetCustomerResolver;

    @Override
    @Transactional
    public BulkBlockResponse bulkBlock(BulkBlockRequest request, UUID adminId) {
        List<UUID> targets = targetCustomerResolver.resolveTargets(
                request.getCustomerIds(),
                request.getSelectAllFiltered(),
                request.getFilters()
        );

        int blockedCount = 0;
        for (UUID customerId : targets) {
            if (blockIfNeeded(customerId, request.getReason(), adminId)) {
                blockedCount++;
            }
        }

        return BulkBlockResponse.builder()
                .blockedCount(blockedCount)
                .build();
    }

    @Override
    @Transactional
    public void blockSingle(UUID customerId, SingleBlockRequest request, UUID adminId) {
        blockIfNeeded(customerId, request.getReason(), adminId);
    }

    @Override
    @Transactional(readOnly = true)
    public
    CustomerBlockHistoryResponse getBlockHistory(UUID customerId) {
        List<CustomerBlockHistoryItemResponse> items = customerBlockRecordRepository
                .findAllByCustomerIdOrderByBlockedAtDesc(customerId)
                .stream()
                .map(entity -> CustomerBlockHistoryItemResponse.builder()
                        .id(entity.getId().toString())
                        .customerId(entity.getCustomerId().toString())
                        .reason(entity.getReason())
                        .blockedAt(entity.getBlockedAt())
                        .blockedByAdminId(entity.getBlockedByAdminId().toString())
                        .active(entity.isActive())
                        .build())
                .toList();

        return CustomerBlockHistoryResponse.builder()
                .items(items)
                .build();
    }

    private boolean blockIfNeeded(UUID customerId, String reason, UUID adminId) {
        boolean alreadyBlocked = customerBlockRecordRepository.findByCustomerIdAndIsActiveTrue(customerId).isPresent();
        if (alreadyBlocked) {
            return false;
        }

        customerAccountCommandPort.blockCustomer(customerId);

        CustomerBlockRecordEntity entity = new CustomerBlockRecordEntity();
        entity.setCustomerId(customerId);
        entity.setReason(reason);
        entity.setBlockedAt(OffsetDateTime.now());
        entity.setBlockedByAdminId(adminId);
        entity.setActive(true);

        customerBlockRecordRepository.save(entity);
        return true;
    }
}
