package com.account.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.account.application.client.CustomerRestClient;
import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;
import com.account.application.mapper.AccountMapper;
import com.account.application.service.AccountService;
import com.account.domain.persistence.Account;
import com.account.domain.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRestClient restClient;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRestClient restClient) {
        this.accountRepository = accountRepository;
        this.restClient = restClient;
    }

    @Override
    public Page<GetAccount> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable).map(AccountMapper::toGetDto);
    }

    @Override
    public FullAccountInfo addAccount(SaveAccount saveAccount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAccount'");
    }

    @Override
    public GetAccount findByNumber(String number) {
        return AccountMapper.toGetDto(findByAccountNumber(number));
    }

    @Override
    public void deleteByNumber(String number) {
        Account account = findByAccountNumber(number);
        account.setDeletedAt(LocalDateTime.now());
        account.setActive(false);
        accountRepository.save(account);

    }

    private Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}
