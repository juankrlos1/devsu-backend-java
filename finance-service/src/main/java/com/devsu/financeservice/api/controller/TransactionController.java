package com.devsu.financeservice.api.controller;

import com.devsu.financeservice.api.dto.CreateTransactionDto;
import com.devsu.financeservice.api.dto.TransactionDto;
import com.devsu.financeservice.common.dto.ApiResponse;
import com.devsu.financeservice.domain.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDto>> createTransaction(@Valid @RequestBody CreateTransactionDto transactionDto) {
        TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(createSuccessResponse("Transaction created successfully", createdTransaction), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(createSuccessResponse("Transactions retrieved successfully", transactions));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionsByClientId(@PathVariable Long clientId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByClientId(clientId);
        return ResponseEntity.ok(createSuccessResponse("Transactions retrieved successfully", transactions));
    }

    private <T> ApiResponse<T> createSuccessResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
