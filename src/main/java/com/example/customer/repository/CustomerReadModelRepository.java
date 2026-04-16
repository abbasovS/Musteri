package com.example.customer.repository;


import com.example.customer.domain.CustomerReadModel;
import com.example.customer.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerReadModelRepository extends JpaRepository<CustomerReadModel, UUID> {

    Page<CustomerReadModel> findByAccountStatusIn(List<AccountStatus> accountStatuses, Pageable pageable);

    long countByAccountStatusIn(List<AccountStatus> accountStatuses);
}
