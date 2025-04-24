package com.account.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.account.domain.util.AccountStatus;
import com.account.domain.util.AccountType;

public record GetAccount(
        Long id,
        String accountNumber,

        AccountType accountType,

        AccountStatus status,

        BigDecimal balance) implements Serializable {

}
