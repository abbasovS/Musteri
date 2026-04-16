package com.example.customer.repository;


import com.example.customer.domain.CustomerProfileMetricsReadModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerProfileMetricsReadModelRepository
        extends JpaRepository<CustomerProfileMetricsReadModel, UUID> {
}
