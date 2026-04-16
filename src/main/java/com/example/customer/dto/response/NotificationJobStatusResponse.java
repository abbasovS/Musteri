package com.example.customer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationJobStatusResponse {

    private String jobId;
    private String status;
    private int requestedCount;
    private int acceptedCount;
    private int sentCount;
    private int failedCount;
    private int skippedCount;
}