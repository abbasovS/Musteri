package com.example.customer.controller;

import com.example.customer.dto.common.ApiResponse;
import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.request.*;
import com.example.customer.dto.response.*;
import com.example.customer.enums.NotificationChannel;
import com.example.customer.service.*;
import com.example.customer.service.port.CustomerCheckinPort;
import com.example.customer.service.port.CustomerPaymentPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class CustomerController {

    private static final UUID SYSTEM_ADMIN_ID = new UUID(0L, 0L);

    private final CustomerAdminQueryService customerAdminQueryService;
    private final CustomerProfileService customerProfileService;
    private final CustomerSubscriptionReadService customerSubscriptionReadService;
    private final ReferenceFilterService referenceFilterService;
    private final CustomerNotificationService customerNotificationService;
    private final CustomerExportService customerExportService;
    private final CustomerBlockingService customerBlockingService;
    private final CustomerPaymentPort customerPaymentPort;
    private final CustomerCheckinPort customerCheckinPort;

    @GetMapping("/customers")
    public ApiResponse<PageResponse<CustomerListItemResponse>> getCustomers(@Valid @ModelAttribute CustomerListRequest request) {
        return ApiResponse.success(customerAdminQueryService.getCustomers(request));
    }

    @GetMapping("/customers/stats")
    public ApiResponse<CustomerStatsResponse> getCustomerStats(
            @RequestParam(defaultValue = "false") boolean applyCurrentFilters,
            @Valid @ModelAttribute CustomerListRequest request
    ) {
        CustomerStatsRequest statsRequest = new CustomerStatsRequest();
        statsRequest.setApplyCurrentFilters(applyCurrentFilters);
        return ApiResponse.success(customerAdminQueryService.getStats(statsRequest, request));
    }

    @GetMapping("/customers/filter-options")
    public ApiResponse<FilterOptionsResponse> getFilterOptions() {
        return ApiResponse.success(referenceFilterService.getFilterOptions());
    }

    @GetMapping("/customers/{customerId}/profile")
    public ApiResponse<CustomerProfileResponse> getCustomerProfile(@PathVariable UUID customerId) {
        return ApiResponse.success(customerProfileService.getProfile(customerId));
    }

    @GetMapping("/customers/{customerId}/subscriptions")
    public ApiResponse<CustomerSubscriptionsResponse> getCustomerSubscriptions(@PathVariable UUID customerId) {
        return ApiResponse.success(customerSubscriptionReadService.getSubscriptions(customerId));
    }

    @GetMapping("/customers/{customerId}/payments")
    public ApiResponse<PageResponse<CustomerPaymentItemResponse>> getCustomerPayments(
            @PathVariable UUID customerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(customerPaymentPort.getPayments(customerId, page, size));
    }

    @GetMapping("/customers/{customerId}/checkins")
    public ApiResponse<PageResponse<CustomerCheckinItemResponse>> getCustomerCheckins(
            @PathVariable UUID customerId,
            @Valid @ModelAttribute CheckinHistoryRequest request
    ) {
        return ApiResponse.success(customerCheckinPort.getCheckins(
                customerId,
                request.getPage(),
                request.getSize(),
                request.getFromDate(),
                request.getToDate()
        ));
    }

    @PostMapping("/customers/notifications/push")
    public ApiResponse<NotificationJobAcceptedResponse> sendBulkPush(
            @Valid @RequestBody BulkPushNotificationRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        return ApiResponse.success(customerNotificationService.sendBulkPush(request, resolveAdminId(adminId)));
    }

    @PostMapping("/customers/notifications/sms")
    public ApiResponse<NotificationJobAcceptedResponse> sendBulkSms(
            @Valid @RequestBody BulkSmsRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        return ApiResponse.success(customerNotificationService.sendBulkSms(request, resolveAdminId(adminId)));
    }

    @PostMapping("/customers/notifications/email")
    public ApiResponse<NotificationJobAcceptedResponse> sendBulkEmail(
            @Valid @RequestBody BulkEmailRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        return ApiResponse.success(customerNotificationService.sendBulkEmail(request, resolveAdminId(adminId)));
    }

    @PostMapping("/customers/{customerId}/notifications/{channel}")
    public ApiResponse<Void> sendSingleNotification(
            @PathVariable UUID customerId,
            @PathVariable String channel,
            @Valid @RequestBody SingleCustomerNotificationRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        request.setChannel(NotificationChannel.valueOf(channel.toUpperCase(Locale.ROOT)));
        customerNotificationService.sendSingleNotification(customerId, request, resolveAdminId(adminId));
        return ApiResponse.successMessage("Notification accepted");
    }

    @GetMapping("/customer-notification-jobs/{jobId}")
    public ApiResponse<NotificationJobStatusResponse> getNotificationJobStatus(@PathVariable UUID jobId) {
        return ApiResponse.success(customerNotificationService.getNotificationJobStatus(jobId));
    }

    @PostMapping("/customers/export")
    public ApiResponse<ExportJobAcceptedResponse> createExportJob(
            @Valid @RequestBody CustomerExportRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        return ApiResponse.success(customerExportService.createExportJob(request, resolveAdminId(adminId)));
    }

    @GetMapping("/customer-exports/{exportJobId}")
    public ApiResponse<ExportJobStatusResponse> getExportJobStatus(@PathVariable UUID exportJobId) {
        return ApiResponse.success(customerExportService.getExportJobStatus(exportJobId));
    }

    @PostMapping("/customers/block")
    public ApiResponse<BulkBlockResponse> bulkBlock(
            @Valid @RequestBody BulkBlockRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        return ApiResponse.success(customerBlockingService.bulkBlock(request, resolveAdminId(adminId)));
    }

    @PostMapping("/customers/{customerId}/block")
    public ApiResponse<Void> blockSingle(
            @PathVariable UUID customerId,
            @Valid @RequestBody SingleBlockRequest request,
            @RequestHeader(value = "X-Admin-Id", required = false) UUID adminId
    ) {
        customerBlockingService.blockSingle(customerId, request, resolveAdminId(adminId));
        return ApiResponse.successMessage("Customer blocked");
    }

    @GetMapping("/customers/{customerId}/block-history")
    public ApiResponse<CustomerBlockHistoryResponse> getBlockHistory(@PathVariable UUID customerId) {
        return ApiResponse.success(customerBlockingService.getBlockHistory(customerId));
    }

    private UUID resolveAdminId(UUID adminId) {
        return adminId != null ? adminId : SYSTEM_ADMIN_ID;
    }
}
