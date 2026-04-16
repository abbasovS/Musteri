package com.example.customer.repository;


import com.example.customer.domain.ExportJobCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExportJobCustomerRepository extends JpaRepository<ExportJobCustomerEntity, UUID> {

    List<ExportJobCustomerEntity> findAllByExportJobId(UUID exportJobId);
}