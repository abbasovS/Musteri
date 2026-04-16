package com.example.customer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBlockHistoryItemResponse {

    private String id;
    private String customerId;
    private String reason;
    private OffsetDateTime blockedAt;
    private String blockedByAdminId;
    private boolean active;
}
