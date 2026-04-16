package com.example.customer.domain;

import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.PlatformType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerReadModel extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "public_id", nullable = false, unique = true)
    private String publicId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private PlatformType platform;

    @Column(name = "is_push_enabled")
    private Boolean isPushEnabled;
}