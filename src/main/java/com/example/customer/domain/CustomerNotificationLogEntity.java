package com.example.customer.domain;


import com.example.customer.enums.NotificationChannel;
import com.example.customer.enums.NotificationDeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "customer_notification_logs",
        indexes = {
                @Index(name = "idx_notification_logs_customer_id", columnList = "customer_id"),
                @Index(name = "idx_notification_logs_channel", columnList = "channel"),
                @Index(name = "idx_notification_logs_status", columnList = "status"),
                @Index(name = "idx_notification_logs_sent_by_admin_id", columnList = "sent_by_admin_id"),
                @Index(name = "idx_notification_logs_sent_at", columnList = "sent_at")
        }
)
public class CustomerNotificationLogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 20)
    private NotificationChannel channel;

    @Column(name = "template_id", length = 255)
    private String templateId;

    @Column(name = "subject", length = 255)
    private String subject;

    @Lob
    @Column(name = "message_body", nullable = false)
    private String messageBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationDeliveryStatus status;

    @Column(name = "provider_message_id", length = 255)
    private String providerMessageId;

    @Column(name = "sent_at")
    private OffsetDateTime sentAt;

    @Column(name = "sent_by_admin_id", nullable = false)
    private UUID sentByAdminId;
}
