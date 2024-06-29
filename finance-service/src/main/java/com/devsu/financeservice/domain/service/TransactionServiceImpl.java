package com.devsu.financeservice.domain.service;

import com.devsu.financeservice.api.dto.CreateTransactionDto;
import com.devsu.financeservice.api.dto.TransactionDto;
import com.devsu.financeservice.domain.model.Account;
import com.devsu.financeservice.domain.model.Transaction;
import com.devsu.financeservice.domain.repository.AccountRepository;
import com.devsu.financeservice.domain.repository.TransactionRepository;
import com.devsu.financeservice.exception.AccountInactiveException;
import com.devsu.financeservice.exception.AccountNotFoundException;
import com.devsu.financeservice.exception.InsufficientFundsException;
import com.devsu.financeservice.infrastructure.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private TransactionMapper transactionMapper;

    @Transactional
    public TransactionDto createTransaction(CreateTransactionDto dto) {
        Account account = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada: " + dto.getAccountNumber()));

        validateTransaction(dto, account);

        boolean isDeposit = "Deposito".equalsIgnoreCase(dto.getTransactionType());
        double transactionAmount = Math.abs(dto.getAmount());
        double newBalance = calculateNewBalance(account, isDeposit, transactionAmount);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setDate(LocalDateTime.now());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(isDeposit ? transactionAmount : -transactionAmount);
        transaction.setBalance(newBalance);

        account.setInitialBalance(newBalance);

        Transaction savedTransaction = transactionRepository.save(transaction);
        accountRepository.save(account);

        return transactionMapper.toDto(savedTransaction);
    }

    private void validateTransaction(CreateTransactionDto dto, Account account) {
        if (!account.getStatus()) {
            throw new AccountInactiveException("La cuenta no está activa");
        }
        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if ("Retiro".equalsIgnoreCase(dto.getTransactionType()) &&
                account.getInitialBalance() < dto.getAmount()) {
            throw new InsufficientFundsException("Saldo no disponible");
        }
    }

    private double calculateNewBalance(Account account, boolean isDeposit, double amount) {
        if (isDeposit) {
            return account.getInitialBalance() + amount;
        } else {
            return account.getInitialBalance() - amount;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionsByAccountNumber(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountAccountNumber(accountNumber);
        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionsByClientId(Long clientId) {
        List<Transaction> transactions = transactionRepository.findByAccountClientId(clientId);
        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }
}
