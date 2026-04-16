package com.example.customer.service.adapter;


import com.example.customer.service.port.SmsProviderPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SmsProviderAdapter implements SmsProviderPort {

    @Override
    public ProviderSendResult send(UUID customerId, String message) {
        return new ProviderSendResult(true, false, "sms-" + UUID.randomUUID());
    }
}
