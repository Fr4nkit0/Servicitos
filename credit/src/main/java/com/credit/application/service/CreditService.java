package com.credit.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.GetCredit;

public interface CreditService {

    Page<GetCredit> findAll(Pageable pageable);

    GetCredit findById(Long id);

    GetCredit create(SaveCredit saveCredit);

    void delete(Long id);

}
