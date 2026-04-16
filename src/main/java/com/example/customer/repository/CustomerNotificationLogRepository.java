package com.example.customer.repository;


import com.example.customer.domain.CustomerNotificationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerNotificationLogRepository extends JpaRepository<CustomerNotificationLogEntity, UUID> {
}
