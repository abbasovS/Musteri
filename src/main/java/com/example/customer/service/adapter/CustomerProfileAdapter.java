package com.example.customer.service.adapter;


import com.example.customer.domain.CustomerProfileMetricsReadModel;
import com.example.customer.repository.CustomerProfileMetricsReadModelRepository;
import com.example.customer.service.port.CustomerProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerProfileAdapter implements CustomerProfilePort {

    private final CustomerProfileMetricsReadModelRepository customerProfileMetricsReadModelRepository;

    @Override
    public Optional<CustomerProfileMetricsReadModel> findProfileMetrics(UUID customerId) {
        return customerProfileMetricsReadModelRepository.findById(customerId);
    }
}