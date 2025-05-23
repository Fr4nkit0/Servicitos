package com.commons.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Deposito(
        @NotBlank(message = "{generic.notBlank}") @Size(max = 30, message = "{generic.size}") @JsonProperty(value = "account_number") String accountNumber,

        @Positive BigDecimal amount) implements Serializable {
}
