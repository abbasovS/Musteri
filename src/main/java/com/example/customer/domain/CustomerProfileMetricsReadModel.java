package com.example.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customer_profile_metrics")
public class CustomerProfileMetricsReadModel {

    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "goal")
    private String goal;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "bmi_value")
    private Double bmiValue;

    @Column(name = "avatar_url")
    private String avatarUrl;
}