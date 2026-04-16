package com.example.customer.service;



import com.example.customer.dto.response.CustomerSubscriptionsResponse;

import java.util.UUID;

public interface CustomerSubscriptionReadService {

    CustomerSubscriptionsResponse getSubscriptions(UUID customerId);
}
