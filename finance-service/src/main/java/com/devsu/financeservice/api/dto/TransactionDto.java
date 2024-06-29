package com.devsu.financeservice.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {
    private Long transactionId;
    private LocalDateTime date;
    private String transactionType;
    private Double amount;
    private Double balance;
    private AccountDto account;
}
