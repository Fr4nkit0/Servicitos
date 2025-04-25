package com.account.application.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.account.domain.util.AccountStatus;
import com.account.domain.util.AccountType;
import com.commons.dto.response.GetCustomerDetail;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GetAccount(
                Long id,
                @JsonProperty(value = "account_number") String accountNumber,

                @JsonProperty(value = "account_type") AccountType accountType,

                AccountStatus status,

                BigDecimal balance,
                GetCustomerDetail customer) implements Serializable {

}
