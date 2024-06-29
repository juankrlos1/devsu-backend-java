package com.devsu.financeservice.domain.repository;

import com.devsu.financeservice.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAccountNumber(String accountNumber);
    List<Transaction> findByAccountClientId(Long clientId);
    List<Transaction> findByAccountAccountNumberAndDateBetween(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
}
