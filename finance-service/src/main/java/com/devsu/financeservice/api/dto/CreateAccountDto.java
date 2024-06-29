package com.devsu.financeservice.api.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAccountDto {
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Initial balance is required")
    private Double initialBalance;

    @NotNull(message = "Status is required")
    private Boolean status;

    @NotNull(message = "Client ID is required")
    private Long clientId;
}
