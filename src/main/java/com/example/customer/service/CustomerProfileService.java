package com.example.customer.service;



import com.example.customer.dto.response.CustomerProfileResponse;

import java.util.UUID;

public interface CustomerProfileService {

    CustomerProfileResponse getProfile(UUID customerId);
}