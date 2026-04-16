package com.example.customer.service.port;

import java.util.UUID;

public interface EmailProviderPort {

    ProviderSendResult send(UUID customerId, String subject, String messageHtml, String messageText);

    record ProviderSendResult(boolean success, boolean skipped, String providerMessageId, String failureReason) {
    }
}