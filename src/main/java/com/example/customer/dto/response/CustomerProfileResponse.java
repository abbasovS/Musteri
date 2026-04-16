package com.example.customer.dto.response;


import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileResponse {

    private String customerId;
    private String publicId;
    private String fullName;
    private String avatarUrl;
    private AccountStatus accountStatus;
    private OffsetDateTime registrationDate;
    private PlatformType platform;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private String goal;
    private Integer heightCm;
    private Double weightKg;
    private Double bmiValue;
}
