package com.example.customer.service.adapter;

import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.response.CustomerPaymentItemResponse;
import com.example.customer.service.port.CustomerPaymentPort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomerPaymentAdapter implements CustomerPaymentPort {

    @Override
    public PageResponse<CustomerPaymentItemResponse> getPayments(UUID customerId, int page, int size) {
        return PageResponse.<CustomerPaymentItemResponse>builder()
                .items(Collections.emptyList())
                .page(page)
                .size(size)
                .totalItems(0)
                .totalPages(0)
                .build();
    }
}
