package com.account.application.service;

import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Page<GetAccount> findAll(Pageable pageable);

    FullAccountInfo addAccount(SaveAccount saveAccount);

    GetAccount findById(Long id);

    GetAccount findByNumber(String number);

    void deleteAccount(Long id);

    void deleteByNumber(String number);
}
