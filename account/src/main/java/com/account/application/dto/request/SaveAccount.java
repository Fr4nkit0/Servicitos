package com.account.application.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.account.domain.util.AccountStatus;
import com.account.domain.util.AccountType;
import com.commons.dto.request.SaveCustomer;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaveAccount(
                @JsonProperty(value = "account_number") @NotBlank(message = "{generic.notBlank}") @Size(max = 30, message = "{generic.size}") String accountNumber,

                @JsonProperty(value = "account_type") @NotNull(message = "{generic.notNull}") AccountType accountType,

                @NotNull(message = "{generic.notNull}") AccountStatus status,

                @NotNull(message = "{generic.notNull}") @DecimalMin(value = "0.0", inclusive = true, message = "{generic.min}") @Digits(integer = 17, fraction = 2, message = "{generic.digits}") BigDecimal balance,

                @NotNull(message = "{generic.notNull}") SaveCustomer customer

) implements Serializable {

}
