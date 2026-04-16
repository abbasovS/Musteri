package com.example.customer.dto.response;

import com.example.customer.dto.common.PageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListResponse extends PageResponse<CustomerListItemResponse> {
}