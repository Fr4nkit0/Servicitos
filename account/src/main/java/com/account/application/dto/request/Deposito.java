package com.account.application.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Deposito(
                @NotBlank(message = "{generic.notBlank}") @Size(max = 30, message = "{generic.size}") String accountNumber,
                @Positive BigDecimal amount) implements Serializable {

}
