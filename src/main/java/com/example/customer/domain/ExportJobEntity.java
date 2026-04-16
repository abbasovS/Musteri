package com.example.customer.domain;


import com.example.customer.enums.ExportFormat;
import com.example.customer.enums.ExportJobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "export_jobs",
        indexes = {
                @Index(name = "idx_export_jobs_requested_by", columnList = "requested_by"),
                @Index(name = "idx_export_jobs_status", columnList = "status"),
                @Index(name = "idx_export_jobs_expires_at", columnList = "expires_at")
        }
)
public class ExportJobEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "requested_by", nullable = false)
    private UUID requestedBy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "filters_json", columnDefinition = "jsonb")
    private String filtersJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false, length = 10)
    private ExportFormat format;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ExportJobStatus status;

    @Column(name = "file_url", length = 1000)
    private String fileUrl;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    

    @Column(name = "select_all_filtered", nullable = false)
    private boolean selectAllFiltered;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "customer_ids_json", columnDefinition = "jsonb")
    private String customerIdsJson;
}