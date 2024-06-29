package com.devsu.financeservice.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDetailDto {
    private LocalDateTime date;
    private String transactionType;
    private Double amount;
    private Double balance;
}