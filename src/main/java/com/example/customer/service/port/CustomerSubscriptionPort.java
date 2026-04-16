package com.example.customer.service.port;



import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CustomerSubscriptionPort {

    Optional<CustomerSubscriptionSnapshotReadModel> getCurrentSubscription(UUID customerId);

    List<CustomerSubscriptionSnapshotReadModel> getSubscriptionHistory(UUID customerId);
    Map<UUID, CustomerSubscriptionSnapshotReadModel> getCurrentSubscriptionsForCustomers(List<UUID> customerIds);
}
