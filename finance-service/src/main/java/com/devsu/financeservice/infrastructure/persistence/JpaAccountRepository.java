package com.devsu.financeservice.infrastructure.persistence;

import com.devsu.financeservice.domain.model.Account;
import com.devsu.financeservice.domain.repository.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
