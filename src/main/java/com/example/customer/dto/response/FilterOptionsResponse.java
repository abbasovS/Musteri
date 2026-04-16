package com.example.customer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptionsResponse {

    private List<String> packages;
    private List<Integer> durations;
    private List<String> subscriptionStatuses;
    private List<String> sortOptions;
}
