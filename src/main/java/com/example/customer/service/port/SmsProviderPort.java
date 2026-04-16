package com.example.customer.service.port;


import java.util.UUID;

public interface SmsProviderPort {

    ProviderSendResult send(UUID customerId, String message);

    record ProviderSendResult(boolean success, boolean skipped, String providerMessageId, String failureReason) {
    }
}