package com.example.customer.service.adapter;


import com.example.customer.service.port.CustomerAccountCommandPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerAccountCommandAdapter implements CustomerAccountCommandPort {

    @Override
    public void blockCustomer(UUID customerId) {
        // TEMP: do nothing
    }
}