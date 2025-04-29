package com.account.application.mapper;

import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;
import com.account.domain.persistence.Account;
import com.commons.dto.response.GetCustomerDetail;

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

    public static Account toEntityFromDto(SaveAccount saveAccount, Long customerId) {
        if (saveAccount == null)
            return null;
        return Account.builder()
                .accountNumber(saveAccount.accountNumber())
                .accountType(saveAccount.accountType())
                .status(saveAccount.status())
                .balance(saveAccount.balance())
                .customerId(customerId)
                .isActive(true)
                .build();
    }

    public static FullAccountInfo toDtoFromEntity(Account account, GetCustomerDetail customer) {
        if (account == null)
            return null;
        return new FullAccountInfo(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType().toString(),
                account.getStatus().toString(),
                account.getBalance(),
                customer);

    }

}
