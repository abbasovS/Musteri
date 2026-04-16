package com.example.customer.dto.response;


import com.example.customer.dto.common.PageResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerPaymentsResponse extends PageResponse<CustomerPaymentItemResponse> {
}
