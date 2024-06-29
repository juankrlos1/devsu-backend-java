package com.devsu.financeservice.domain.service;

import com.devsu.financeservice.api.dto.CreateTransactionDto;
import com.devsu.financeservice.api.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(CreateTransactionDto transactionDto);
    List<TransactionDto> getTransactionsByAccountNumber(String accountNumber);
    List<TransactionDto> getTransactionsByClientId(Long clientId);
}
