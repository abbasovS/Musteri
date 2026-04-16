package com.example.customer.service.port;


import java.util.UUID;

public interface CustomerAccountCommandPort {

    void blockCustomer(UUID customerId);

    void unblockCustomer(UUID customerId);
}