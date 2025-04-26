package com.account.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.commons.dto.response.GetCustomerDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FullAccountInfo(
        Long id,
        @JsonProperty(value = "account_number") String accountNumber,

        @JsonProperty(value = "account_type") String accountType,

        String status,

        BigDecimal balance,
        GetCustomerDetail customer) implements Serializable {

}
