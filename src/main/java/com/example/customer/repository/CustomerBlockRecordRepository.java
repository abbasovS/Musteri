package com.example.customer.repository;


import com.example.customer.domain.CustomerBlockRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerBlockRecordRepository extends JpaRepository<CustomerBlockRecordEntity, UUID> {

    List<CustomerBlockRecordEntity> findAllByCustomerIdOrderByBlockedAtDesc(UUID customerId);

    Optional<CustomerBlockRecordEntity> findByCustomerIdAndIsActiveTrue(UUID customerId);
}
