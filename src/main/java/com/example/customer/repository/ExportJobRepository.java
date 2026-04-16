package com.example.customer.repository;


import com.example.customer.domain.ExportJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExportJobRepository extends JpaRepository<ExportJobEntity, UUID> {
}
