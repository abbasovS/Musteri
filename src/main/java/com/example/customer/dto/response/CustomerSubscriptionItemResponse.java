package com.example.customer.dto.response;


import com.example.customer.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSubscriptionItemResponse {

    private String packageCode;
    private String packageName;
    private Integer durationMonths;
    private SubscriptionStatus subscriptionStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer daysRemaining;
}