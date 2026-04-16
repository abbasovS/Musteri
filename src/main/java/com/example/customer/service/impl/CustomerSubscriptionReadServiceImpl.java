package com.example.customer.service.impl;

import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;
import com.example.customer.dto.response.CustomerSubscriptionsResponse;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.service.port.CustomerSubscriptionPort;
import com.example.customer.service.CustomerSubscriptionReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerSubscriptionReadServiceImpl implements CustomerSubscriptionReadService {

    private final CustomerSubscriptionPort customerSubscriptionPort;

    @Override
    public CustomerSubscriptionsResponse getSubscriptions(UUID customerId) {
        CustomerSubscriptionSnapshotReadModel current = customerSubscriptionPort.getCurrentSubscription(customerId).orElse(null);
        List<CustomerSubscriptionSnapshotReadModel> history = customerSubscriptionPort.getSubscriptionHistory(customerId);

        return CustomerMapper.toSubscriptionsResponse(current, history);
    }
}