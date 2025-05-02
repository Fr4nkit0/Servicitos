package com.commons.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetAccount(
        long id,
        @JsonProperty(value = "account_number") String accountNumber,
        @JsonProperty(value = "account_type") String accountType,
        String status,
        BigDecimal balance,
        long customerId) {

}
