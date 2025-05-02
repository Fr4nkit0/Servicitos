package com.credit.application.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.credit.domain.util.CreditType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SaveCredit(
        @NotNull(message = "{generic.notNull}") @DecimalMin(value = "0.0", inclusive = false, message = "{generic.min}") @Digits(integer = 17, fraction = 2, message = "{generic.digits}") BigDecimal amount,
        @JsonProperty(value = "term_months") @NotNull(message = "{generic.notNull}") @Min(value = 1, message = "{generic.min}") @Max(value = 600, message = "{generic.max}") Integer termMonths,
        @JsonProperty(value = "interest_rate") @NotNull(message = "{generic.notNull}") @DecimalMin(value = "0.0001", inclusive = true, message = "{generic.min}") @Digits(integer = 1, fraction = 4, message = "{generic.digits}") BigDecimal interestRate,
        @JsonProperty(value = "credit_type") @NotNull(message = "{generic.notNull}") CreditType creditType,
        @JsonProperty(value = "account_number") @NotBlank(message = "{generic.notBlank}") @Size(max = 30, message = "{generic.size}") String accountNumber,
        @JsonProperty(value = "customer_id") @NotNull(message = "{generic.notNull}") @Positive(message = "{generic.positive}") Long clientId)
        implements Serializable {

}
