package com.example.customer.service.impl;

import com.example.customer.dto.request.BulkTargetFilterRequest;
import com.example.customer.exception.ValidationException;
import com.example.customer.service.port.CustomerAdminReadPort;
import com.example.customer.service.TargetCustomerResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TargetCustomerResolverImpl implements TargetCustomerResolver {

    private final CustomerAdminReadPort customerAdminReadPort;

    @Override
    public List<UUID> resolveTargets(List<UUID> customerIds, Boolean selectAllFiltered, BulkTargetFilterRequest filters) {
        boolean hasIds = customerIds != null && !customerIds.isEmpty();
        boolean selectFiltered = Boolean.TRUE.equals(selectAllFiltered);

        if (!hasIds && !selectFiltered) {
            throw new ValidationException("Either customerIds or selectAllFiltered=true must be provided");
        }

        if (hasIds && selectFiltered) {
            throw new ValidationException("customerIds and selectAllFiltered cannot be used together");
        }

        if (selectFiltered && filters == null) {
            throw new ValidationException("filters must be provided when selectAllFiltered=true");
        }

        Set<UUID> resolved = new LinkedHashSet<>();

        if (hasIds) {
            resolved.addAll(customerIds);
        } else {
            resolved.addAll(customerAdminReadPort.findCustomerIdsByFilters(filters));
        }

        if (resolved.isEmpty()) {
            throw new ValidationException("No target customers found");
        }

        return List.copyOf(resolved);
    }
}
