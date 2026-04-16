package com.example.customer.service.port;


import com.example.customer.dto.common.PageResponse;
import com.example.customer.dto.response.CustomerCheckinItemResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface CustomerCheckinPort {

    PageResponse<CustomerCheckinItemResponse> getCheckins(UUID customerId, int page, int size, LocalDate fromDate, LocalDate toDate);
}
