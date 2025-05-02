package com.credit.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCredit(
        Long id,
        BigDecimal amount,
        @JsonProperty(value = "term_months") Integer termMonths,
        @JsonProperty(value = "interest_rate") BigDecimal interestRate,
        @JsonProperty(value = "credit_type") String creditType,
        @JsonProperty(value = "account_number") String accountNumber,
        @JsonProperty(value = "cliente_id") Long clientId) implements Serializable {
}
