package com.example.customer.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "export_job_customers",
        indexes = {
                @Index(name = "idx_export_job_customers_job_id", columnList = "export_job_id"),
                @Index(name = "idx_export_job_customers_customer_id", columnList = "customer_id")
        }
)
public class ExportJobCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "export_job_id", nullable = false)
    private ExportJobEntity exportJob;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
}
