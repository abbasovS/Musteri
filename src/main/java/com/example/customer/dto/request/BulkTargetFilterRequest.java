package com.example.customer.dto.request;


import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkTargetFilterRequest {

    private String search;
    private List<String> packageCodes;
    private List<Integer> durationMonths;
    private List<SubscriptionStatus> subscriptionStatuses;
    private List<AccountStatus> accountStatuses;
}