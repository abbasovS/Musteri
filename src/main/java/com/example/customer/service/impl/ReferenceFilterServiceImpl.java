package com.example.customer.service.impl;


import com.example.customer.dto.response.FilterOptionsResponse;
import com.example.customer.enums.SubscriptionStatus;
import com.example.customer.service.ReferenceFilterService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReferenceFilterServiceImpl implements ReferenceFilterService {

    @Override
    public FilterOptionsResponse getFilterOptions() {
        return FilterOptionsResponse.builder()
                .packages(List.of("BRONZE", "SILVER", "GOLD", "PLATINUM"))
                .durations(List.of(1, 2, 3))
                .subscriptionStatuses(Arrays.stream(SubscriptionStatus.values())
                        .map(Enum::name)
                        .toList())
                .sortOptions(List.of(
                        "NEWEST",
                        "NAME_ASC",
                        "NAME_DESC",
                        "SUBSCRIPTION_END_ASC",
                        "SUBSCRIPTION_END_DESC",
                        "REGISTRATION_ASC",
                        "REGISTRATION_DESC"
                ))
                .build();
    }
}
