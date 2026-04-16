package com.example.customer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatsResponse {

    private long totalCustomers;
    private long endingIn7Days;
    private long expiredSubscriptions;
    private long activeSubscriptions;
}
