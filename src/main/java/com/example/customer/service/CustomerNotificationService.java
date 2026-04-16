package com.example.customer.service;


import com.example.customer.dto.request.BulkEmailRequest;
import com.example.customer.dto.request.BulkPushNotificationRequest;
import com.example.customer.dto.request.BulkSmsRequest;
import com.example.customer.dto.request.SingleCustomerNotificationRequest;
import com.example.customer.dto.response.NotificationJobAcceptedResponse;
import com.example.customer.dto.response.NotificationJobStatusResponse;

import java.util.UUID;

public interface CustomerNotificationService {

    NotificationJobAcceptedResponse sendBulkPush(BulkPushNotificationRequest request, UUID adminId);

    NotificationJobAcceptedResponse sendBulkSms(BulkSmsRequest request, UUID adminId);

    NotificationJobAcceptedResponse sendBulkEmail(BulkEmailRequest request, UUID adminId);

    void sendSingleNotification(UUID customerId, SingleCustomerNotificationRequest request, UUID adminId);

    NotificationJobStatusResponse getNotificationJobStatus(UUID jobId);
}