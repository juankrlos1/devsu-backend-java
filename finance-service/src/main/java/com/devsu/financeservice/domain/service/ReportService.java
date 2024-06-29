package com.devsu.financeservice.domain.service;

import com.devsu.financeservice.api.dto.*;
import com.devsu.financeservice.common.dto.ApiResponse;
import com.devsu.financeservice.domain.model.Account;
import com.devsu.financeservice.domain.model.Transaction;
import com.devsu.financeservice.domain.repository.AccountRepository;
import com.devsu.financeservice.domain.repository.TransactionRepository;
import com.devsu.financeservice.infrastructure.external.ClientServiceClient;
import com.devsu.financeservice.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientServiceClient clientServiceClient;

    @Transactional(readOnly = true)
    public List<AccountStatementDto> getAccountStatements(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        ClientDto client = getClientById(clientId);
        List<Account> accounts = accountRepository.findByClientId(clientId);

        return accounts.stream()
                .map(account -> createAccountStatement(account, client, startDate, endDate))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountStatementDto getAccountStatementByAccountNumber(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with account number: " + accountNumber));

        ClientDto client = getClientById(account.getClientId());
        return createAccountStatement(account, client, startDate, endDate);
    }

    private ClientDto getClientById(Long clientId) {
        ApiResponse<ClientDto> clientResponse = clientServiceClient.getClientById(clientId);
        ClientDto client = clientResponse.getData();
        log.info("Client response: {}", client);
        return client;
    }

    private AccountStatementDto createAccountStatement(Account account, ClientDto client, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionDetailDto> transactionDetails = getTransactionDetails(account.getAccountNumber(), startDate, endDate);

        double availableBalance = calculateAvailableBalance(account.getInitialBalance(), transactionDetails);

        return AccountStatementDto.builder()
                .clientName(client.getName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialBalance(account.getInitialBalance())
                .availableBalance(availableBalance)
                .transactions(transactionDetails)
                .build();
    }

    private List<TransactionDetailDto> getTransactionDetails(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository
                .findByAccountAccountNumberAndDateBetween(accountNumber, startDate, endDate);

        return transactions.stream()
                .map(this::mapToTransactionDetailDto)
                .collect(Collectors.toList());
    }

    private TransactionDetailDto mapToTransactionDetailDto(Transaction transaction) {
        return TransactionDetailDto.builder()
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .build();
    }

    private double calculateAvailableBalance(double initialBalance, List<TransactionDetailDto> transactions) {
        return initialBalance + transactions.stream()
                .mapToDouble(TransactionDetailDto::getAmount)
                .sum();
    }
}