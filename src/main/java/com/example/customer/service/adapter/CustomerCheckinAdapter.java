package com.example.customer.service.adapter;

import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.response.CustomerCheckinItemResponse;
import com.example.customer.service.port.CustomerCheckinPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

@Service
public class CustomerCheckinAdapter implements CustomerCheckinPort {

    @Override
    public PageResponse<CustomerCheckinItemResponse> getCheckins(UUID customerId, int page, int size, LocalDate fromDate, LocalDate toDate) {
        return PageResponse.<CustomerCheckinItemResponse>builder()
                .items(Collections.emptyList())
                .page(page)
                .size(size)
                .totalItems(0)
                .totalPages(0)
                .build();
    }
}
