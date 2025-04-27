package com.account.application.mapper;

import com.account.application.dto.response.GetAccount;
import com.account.domain.persistence.Account;

public class AccountMapper {

    public static GetAccount toGetDto(Account account) {
        if (account == null)
            return null;

        return new GetAccount(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType().toString(),
                account.getStatus().toString(),
                account.getBalance(),
                account.getCustomerId());
    }

}
