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
public class ExportJobStatusResponse {

    private String exportJobId;
    private String status;
    private String fileUrl;
    private OffsetDateTime expiresAt;
}
