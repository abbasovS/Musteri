package com.example.customer.mapper;


import com.example.customer.domain.CustomerProfileMetricsReadModel;
import com.example.customer.domain.CustomerReadModel;
import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;
import com.example.customer.dto.response.CustomerListItemResponse;
import com.example.customer.dto.response.CustomerProfileResponse;
import com.example.customer.dto.response.CustomerSubscriptionItemResponse;
import com.example.customer.dto.response.CustomerSubscriptionsResponse;

import java.util.List;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerListItemResponse toListItem(CustomerReadModel customer, CustomerSubscriptionSnapshotReadModel currentSubscription) {
        return CustomerListItemResponse.builder()
                .id(customer.getId().toString())
                .publicId(customer.getPublicId())
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .accountStatus(customer.getAccountStatus())
                .subscriptionStatus(currentSubscription != null ? currentSubscription.getSubscriptionStatus() : null)
                .packageCode(currentSubscription != null ? currentSubscription.getPackageCode() : null)
                .build();
    }

    public static CustomerProfileResponse toProfile(CustomerReadModel customer, CustomerProfileMetricsReadModel metrics) {
        return CustomerProfileResponse.builder()
                .customerId(customer.getId().toString())
                .publicId(customer.getPublicId())
                .fullName(customer.getFullName())
                .avatarUrl(metrics != null ? metrics.getAvatarUrl() : null)
                .accountStatus(customer.getAccountStatus())
                .registrationDate(customer.getRegistrationDate())
                .platform(customer.getPlatform())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .birthDate(metrics != null ? metrics.getBirthDate() : null)
                .goal(metrics != null ? metrics.getGoal() : null)
                .heightCm(metrics != null ? metrics.getHeightCm() : null)
                .weightKg(metrics != null ? metrics.getWeightKg() : null)
                .bmiValue(metrics != null ? metrics.getBmiValue() : null)
                .build();
    }

    public static CustomerSubscriptionItemResponse toSubscriptionItem(CustomerSubscriptionSnapshotReadModel model) {
        return CustomerSubscriptionItemResponse.builder()
                .packageCode(model.getPackageCode())
                .packageName(model.getPackageName())
                .durationMonths(model.getDurationMonths())
                .subscriptionStatus(model.getSubscriptionStatus())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .daysRemaining(model.getDaysRemaining())
                .build();
    }

    public static CustomerSubscriptionsResponse toSubscriptionsResponse(
            CustomerSubscriptionSnapshotReadModel current,
            List<CustomerSubscriptionSnapshotReadModel> history
    ) {
        return CustomerSubscriptionsResponse.builder()
                .currentSubscription(current != null ? toSubscriptionItem(current) : null)
                .subscriptionHistory(history.stream().map(CustomerMapper::toSubscriptionItem).toList())
                .build();
    }
}