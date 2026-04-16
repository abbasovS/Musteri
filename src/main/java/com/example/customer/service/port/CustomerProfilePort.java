package com.example.customer.service.port;



import com.example.customer.domain.CustomerProfileMetricsReadModel;

import java.util.Optional;
import java.util.UUID;

public interface CustomerProfilePort {

    Optional<CustomerProfileMetricsReadModel> findProfileMetrics(UUID customerId);
}