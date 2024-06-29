package com.devsu.financeservice.domain.repository;

import com.devsu.financeservice.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(Long accountId);
    Account save(Account account);
    void deleteById(Long accountId);
    boolean existsById(Long accountId);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByClientId(Long clientId);
}
