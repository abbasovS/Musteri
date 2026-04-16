package com.example.customer.dto.response;


import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.SubscriptionStatus;
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
public class CustomerListItemResponse {

    private String id;
    private String publicId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private AccountStatus accountStatus;
    private SubscriptionStatus subscriptionStatus;
    private String packageCode;
}
