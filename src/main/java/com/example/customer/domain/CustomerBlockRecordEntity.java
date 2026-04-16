package com.example.customer.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "customer_block_records",
        indexes = {
                @Index(name = "idx_block_records_customer_id", columnList = "customer_id"),
                @Index(name = "idx_block_records_blocked_by_admin_id", columnList = "blocked_by_admin_id"),
                @Index(name = "idx_block_records_is_active", columnList = "is_active"),
                @Index(name = "idx_block_records_blocked_at", columnList = "blocked_at")
        }
)
public class CustomerBlockRecordEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "blocked_at", nullable = false)
    private OffsetDateTime blockedAt;

    @Column(name = "blocked_by_admin_id", nullable = false)
    private UUID blockedByAdminId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}