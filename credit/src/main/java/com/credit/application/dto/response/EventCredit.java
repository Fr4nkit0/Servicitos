package com.credit.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventCredit(
        @JsonProperty(value = "cliente_id") Long clientId,
        @JsonProperty(value = "account_number") String accountNumber,
        String email,
        BigDecimal amount) implements Serializable {
}
