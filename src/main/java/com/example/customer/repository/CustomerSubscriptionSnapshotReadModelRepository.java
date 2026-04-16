package com.example.customer.repository;


import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;
import com.example.customer.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CustomerSubscriptionSnapshotReadModelRepository
        extends JpaRepository<CustomerSubscriptionSnapshotReadModel, UUID> {

    List<CustomerSubscriptionSnapshotReadModel> findByCustomerIdIn(Collection<UUID> customerIds);

    long countBySubscriptionStatus(SubscriptionStatus subscriptionStatus);

    long countByEndDateBetween(LocalDate from, LocalDate to);
}