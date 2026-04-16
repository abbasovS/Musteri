package com.example.customer.service.adapter;



import com.example.customer.service.port.PushProviderPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PushProviderAdapter implements PushProviderPort {

    @Override
    public ProviderSendResult send(UUID customerId, String title, String message, String deepLink) {
        // TEMP: fake response
        return new ProviderSendResult(true, false, "push-" + UUID.randomUUID());
    }
}