package com.devsu.financeservice.domain.service;

import com.devsu.financeservice.api.dto.AccountDto;
import com.devsu.financeservice.api.dto.CreateAccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(Long accountId);
    AccountDto createAccount(CreateAccountDto createAccountDto);
    AccountDto updateAccount(Long accountId, CreateAccountDto createAccountDto);
    void deleteAccount(Long accountId);
    AccountDto getAccountByAccountNumber(String accountNumber);
    List<AccountDto> getAccountsByClientId(Long clientId);
}
