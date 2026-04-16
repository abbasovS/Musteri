package com.example.customer.service.impl;

import com.example.customer.domain.CustomerProfileMetricsReadModel;
import com.example.customer.domain.CustomerReadModel;
import com.example.customer.dto.response.CustomerProfileResponse;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.service.port.CustomerAdminReadPort;
import com.example.customer.service.port.CustomerProfilePort;
import com.example.customer.service.CustomerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService {

    private final CustomerAdminReadPort customerAdminReadPort;
    private final CustomerProfilePort customerProfilePort;

    @Override
    public CustomerProfileResponse getProfile(UUID customerId) {
        CustomerReadModel customer = customerAdminReadPort.findCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));

        CustomerProfileMetricsReadModel metrics = customerProfilePort.findProfileMetrics(customerId).orElse(null);

        return CustomerMapper.toProfile(customer, metrics);
    }
}
