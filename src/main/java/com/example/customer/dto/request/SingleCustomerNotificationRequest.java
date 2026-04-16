package com.example.customer.dto.request;


import com.example.customer.enums.NotificationChannel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidSingleCustomerNotificationRequest
public class SingleCustomerNotificationRequest {

    @NotNull
    private NotificationChannel channel;

    @Size(max = 200)
    private String title;

    @Size(max = 255)
    private String subject;

    @Size(max = 20000)
    private String messageHtml;

    @Size(max = 10000)
    private String messageText;

    @Size(max = 2000)
    private String message;

    @Size(max = 500)
    private String deepLink;
}