package com.example.customer.service.impl;

import com.example.customer.domain.CustomerNotificationLogEntity;
import com.example.customer.dto.request.BulkEmailRequest;
import com.example.customer.dto.request.BulkPushNotificationRequest;
import com.example.customer.dto.request.BulkSmsRequest;
import com.example.customer.dto.request.SingleCustomerNotificationRequest;
import com.example.customer.dto.response.NotificationJobAcceptedResponse;
import com.example.customer.dto.response.NotificationJobStatusResponse;
import com.example.customer.enums.NotificationChannel;
import com.example.customer.enums.NotificationDeliveryStatus;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.repository.CustomerNotificationLogRepository;
import com.example.customer.service.port.EmailProviderPort;
import com.example.customer.service.port.PushProviderPort;
import com.example.customer.service.port.SmsProviderPort;
import com.example.customer.service.TargetCustomerResolver;
import com.example.customer.service.CustomerNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerNotificationServiceImpl implements CustomerNotificationService {

    private final TargetCustomerResolver targetCustomerResolver;
    private final CustomerNotificationLogRepository notificationLogRepository;
    private final PushProviderPort pushProviderPort;
    private final SmsProviderPort smsProviderPort;
    private final EmailProviderPort emailProviderPort;

    @Override
    @Transactional
    public NotificationJobAcceptedResponse sendBulkPush(BulkPushNotificationRequest request, UUID adminId) {
        List<UUID> targets = targetCustomerResolver.resolveTargets(
                request.getCustomerIds(),
                request.getSelectAllFiltered(),
                request.getFilters()
        );

        UUID jobId = UUID.randomUUID();

        for (UUID customerId : targets) {
            PushProviderPort.ProviderSendResult result =
                    pushProviderPort.send(customerId, request.getTitle(), request.getMessage(), request.getDeepLink());

            saveLog(customerId, NotificationChannel.PUSH, request.getTitle(), request.getMessage(), adminId, result);
        }

        return NotificationJobAcceptedResponse.builder()
                .jobId(jobId.toString())
                .requestedCount(targets.size())
                .acceptedForProcessing(targets.size())
                .build();
    }

    @Override
    @Transactional
    public NotificationJobAcceptedResponse sendBulkSms(BulkSmsRequest request, UUID adminId) {
        List<UUID> targets = targetCustomerResolver.resolveTargets(
                request.getCustomerIds(),
                request.getSelectAllFiltered(),
                request.getFilters()
        );

        UUID jobId = UUID.randomUUID();

        for (UUID customerId : targets) {
            SmsProviderPort.ProviderSendResult result = smsProviderPort.send(customerId, request.getMessage());
            saveLog(customerId, NotificationChannel.SMS, null, request.getMessage(), adminId, result);
        }

        return NotificationJobAcceptedResponse.builder()
                .jobId(jobId.toString())
                .requestedCount(targets.size())
                .acceptedForProcessing(targets.size())
                .build();
    }

    @Override
    @Transactional
    public NotificationJobAcceptedResponse sendBulkEmail(BulkEmailRequest request, UUID adminId) {
        List<UUID> targets = targetCustomerResolver.resolveTargets(
                request.getCustomerIds(),
                request.getSelectAllFiltered(),
                request.getFilters()
        );

        UUID jobId = UUID.randomUUID();
        String body = request.getMessageHtml() != null ? request.getMessageHtml() : request.getMessageText();

        for (UUID customerId : targets) {
            EmailProviderPort.ProviderSendResult result =
                    emailProviderPort.send(customerId, request.getSubject(), request.getMessageHtml(), request.getMessageText());

            saveLog(customerId, NotificationChannel.EMAIL, request.getSubject(), body, adminId, result);
        }

        return NotificationJobAcceptedResponse.builder()
                .jobId(jobId.toString())
                .requestedCount(targets.size())
                .acceptedForProcessing(targets.size())
                .build();
    }

    @Override
    @Transactional
    public void sendSingleNotification(UUID customerId, SingleCustomerNotificationRequest request, UUID adminId) {
        switch (request.getChannel()) {
            case PUSH -> {
                PushProviderPort.ProviderSendResult result =
                        pushProviderPort.send(customerId, request.getTitle(), request.getMessage(), request.getDeepLink());

                saveLog(
                        customerId,
                        NotificationChannel.PUSH,
                        request.getTitle(),
                        request.getMessage(),
                        adminId,
                        result
                );
            }
            case SMS -> {
                SmsProviderPort.ProviderSendResult result =
                        smsProviderPort.send(customerId, request.getMessage());

                saveLog(
                        customerId,
                        NotificationChannel.SMS,
                        null,
                        request.getMessage(),
                        adminId,
                        result
                );
            }
            case EMAIL -> {
                EmailProviderPort.ProviderSendResult result =
                        emailProviderPort.send(
                                customerId,
                                request.getSubject(),
                                request.getMessageHtml(),
                                request.getMessageText()
                        );

                String body = request.getMessageHtml() != null
                        ? request.getMessageHtml()
                        : request.getMessageText();

                saveLog(
                        customerId,
                        NotificationChannel.EMAIL,
                        request.getSubject(),
                        body,
                        adminId,
                        result
                );
            }
        }
    }

    @Override
    public NotificationJobStatusResponse getNotificationJobStatus(UUID jobId) {
        throw new ResourceNotFoundException("Notification job not implemented yet for jobId: " + jobId);
    }

    private void saveLog(
            UUID customerId,
            NotificationChannel channel,
            String subjectOrTitle,
            String body,
            UUID adminId,
            PushProviderPort.ProviderSendResult result
    ) {
        saveLog(
                customerId,
                channel,
                subjectOrTitle,
                body,
                adminId,
                result.success(),
                result.skipped(),
                result.providerMessageId()
        );
    }

    private void saveLog(
            UUID customerId,
            NotificationChannel channel,
            String subjectOrTitle,
            String body,
            UUID adminId,
            SmsProviderPort.ProviderSendResult result
    ) {
        saveLog(
                customerId,
                channel,
                subjectOrTitle,
                body,
                adminId,
                result.success(),
                result.skipped(),
                result.providerMessageId()
        );
    }

    private void saveLog(
            UUID customerId,
            NotificationChannel channel,
            String subjectOrTitle,
            String body,
            UUID adminId,
            EmailProviderPort.ProviderSendResult result
    ) {
        saveLog(
                customerId,
                channel,
                subjectOrTitle,
                body,
                adminId,
                result.success(),
                result.skipped(),
                result.providerMessageId()
        );
    }

    private void saveLog(
            UUID customerId,
            NotificationChannel channel,
            String subjectOrTitle,
            String body,
            UUID adminId,
            boolean success,
            boolean skipped,
            String providerMessageId
    ) {
        CustomerNotificationLogEntity entity = new CustomerNotificationLogEntity();
        entity.setCustomerId(customerId);
        entity.setChannel(channel);
        entity.setSubject(subjectOrTitle);
        entity.setMessageBody(body);
        entity.setProviderMessageId(providerMessageId);
        entity.setSentByAdminId(adminId);
        entity.setSentAt(OffsetDateTime.now());
        entity.setStatus(resolveStatus(success, skipped));

        notificationLogRepository.save(entity);
    }

    private NotificationDeliveryStatus resolveStatus(boolean success, boolean skipped) {
        if (skipped) {
            return NotificationDeliveryStatus.SKIPPED;
        }
        return success ? NotificationDeliveryStatus.SENT : NotificationDeliveryStatus.FAILED;
    }
}