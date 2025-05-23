package com.credit.application.mapper;

import com.commons.dto.request.Deposito;
import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.GetCredit;
import com.credit.application.event.EventCredit;
import com.credit.domain.persistence.Credit;

public class CreditMapper {

    public static GetCredit toGetDto(Credit credit) {
        if (credit == null)
            return null;
        return new GetCredit(
                credit.getId(),
                credit.getAmount(),
                credit.getTermMonths(),
                credit.getInterestRate(),
                credit.getCreditType().toString(),
                credit.getAccountNumber(),
                credit.getClientId());
    }

    public static Credit toEntityFromDto(SaveCredit credit) {
        if (credit == null)
            return null;
        return Credit.builder()
                .amount(credit.amount())
                .termMonths(credit.termMonths())
                .interestRate(credit.interestRate())
                .creditType(credit.creditType())
                .accountNumber(credit.accountNumber())
                .clientId(credit.clientId())
                .isActive(true)
                .build();
    }

    public static Deposito toDeposito(SaveCredit credit) {
        if (credit == null)
            return null;
        {
        }
        return new Deposito(
                credit.accountNumber(),
                credit.amount());
    }

    public static EventCredit toEvent(Credit credit) {
        if (credit == null)
            return null;
        return new EventCredit(
                credit.getClientId(),
                credit.getAccountNumber(),
                credit.getAmount());
    }
}
