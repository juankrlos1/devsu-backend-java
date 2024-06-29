package com.devsu.financeservice.domain.service;

import com.devsu.financeservice.api.dto.AccountDto;
import com.devsu.financeservice.api.dto.ClientDto;
import com.devsu.financeservice.api.dto.CreateAccountDto;
import com.devsu.financeservice.common.dto.ApiResponse;
import com.devsu.financeservice.domain.model.Account;
import com.devsu.financeservice.domain.repository.AccountRepository;
import com.devsu.financeservice.infrastructure.external.ClientServiceClient;
import com.devsu.financeservice.infrastructure.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientServiceClient clientServiceClient;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        ApiResponse<ClientDto> clientDtoResponse = clientServiceClient.getClientById(createAccountDto.getClientId());

        ClientDto clientDto = clientDtoResponse.getData();

        if (clientDto == null) {
            throw new RuntimeException("Client not found");
        }

        Account account = accountMapper.toEntity(createAccountDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional
    public AccountDto updateAccount(Long accountId, CreateAccountDto createAccountDto) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountMapper.updateEntityFromDto(createAccountDto, existingAccount);
        Account savedAccount = accountRepository.save(existingAccount);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAccountsByClientId(Long clientId) {
        List<Account> accounts = accountRepository.findByClientId(clientId);
        return accounts.stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }
}
