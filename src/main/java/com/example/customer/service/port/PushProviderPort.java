package com.example.customer.service.port;


import java.util.UUID;

public interface PushProviderPort {

    ProviderSendResult send(UUID customerId, String title, String message, String deepLink);

    record ProviderSendResult(boolean success, boolean skipped, String providerMessageId, String failureReason) {
    }
}
