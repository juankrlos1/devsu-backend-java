package com.devsu.financeservice.domain.model;

import com.devsu.financeservice.common.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "client_id", nullable = false)
    private Long clientId;
}
