package com.credit.application.mapper;

import com.credit.application.dto.response.GetCredit;
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

}
