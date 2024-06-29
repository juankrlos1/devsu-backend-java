package com.devsu.financeservice.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AccountStatementDto {
    private String clientName;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Double availableBalance;
    private List<TransactionDetailDto> transactions;
}