package com.example.customer.service.adapter;


import com.example.customer.domain.CustomerSubscriptionSnapshotReadModel;
import com.example.customer.repository.CustomerSubscriptionSnapshotReadModelRepository;
import com.example.customer.service.port.CustomerSubscriptionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerSubscriptionAdapter implements CustomerSubscriptionPort {

    private final CustomerSubscriptionSnapshotReadModelRepository customerSubscriptionSnapshotReadModelRepository;

    @Override
    public Optional<CustomerSubscriptionSnapshotReadModel> getCurrentSubscription(UUID customerId) {
        return customerSubscriptionSnapshotReadModelRepository.findById(customerId);
    }

    @Override
    public List<CustomerSubscriptionSnapshotReadModel> getSubscriptionHistory(UUID customerId) {
        // Səndə ayrıca history entity/repo yoxdur.
        // Hələlik current snapshot-u history kimi tək item qaytarırıq.
        return customerSubscriptionSnapshotReadModelRepository.findById(customerId)
                .map(List::of)
                .orElse(Collections.emptyList());
    }

    @Override
    public Map<UUID, CustomerSubscriptionSnapshotReadModel> getCurrentSubscriptionsForCustomers(List<UUID> customerIds) {
        return customerSubscriptionSnapshotReadModelRepository.findByCustomerIdIn(customerIds)
                .stream()
                .collect(LinkedHashMap::new,
                        (map, item) -> map.put(item.getCustomerId(), item),
                        LinkedHashMap::putAll);
    }
}
