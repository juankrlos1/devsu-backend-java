package com.devsu.financeservice.api.dto;

import com.devsu.financeservice.common.enums.TransactionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CreateTransactionDto {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull(message = "Amount is required")
    private Double amount;

}
