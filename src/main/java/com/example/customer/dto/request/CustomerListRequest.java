package com.example.customer.dto.request;

import com.example.customer.enums.AccountStatus;
import com.example.customer.enums.SubscriptionStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CustomerListRequest {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt",
            "fullName",
            "subscriptionEndDate",
            "registrationDate"
    );

    private String search;

    private List<String> packageCodes;

    private List<Integer> durationMonths;

    private List<SubscriptionStatus> subscriptionStatuses;

    private List<AccountStatus> accountStatuses;

    private String sortBy = "createdAt";

    private Sort.Direction sortOrder = Sort.Direction.DESC;

    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(100)
    private int size = 10;

    @AssertTrue(message = "sortBy contains unsupported field")
    public boolean isSortByValid() {
        return sortBy == null || ALLOWED_SORT_FIELDS.contains(sortBy);
    }
}