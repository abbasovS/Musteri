package com.example.customer.service.adapter;


import com.example.customer.service.port.EmailProviderPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailProviderAdapter implements EmailProviderPort {

    @Override
    public ProviderSendResult send(UUID customerId, String subject, String html, String text) {
        return new ProviderSendResult(true, false, "email-" + UUID.randomUUID());
    }
}