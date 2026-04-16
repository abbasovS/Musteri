package com.example.customer.service.port;



import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.response.CustomerPaymentItemResponse;

import java.util.UUID;

public interface CustomerPaymentPort {

    PageResponse<CustomerPaymentItemResponse> getPayments(UUID customerId, int page, int size);
}
