package com.devsu.financeservice.api.controller;

import com.devsu.financeservice.api.dto.AccountDto;
import com.devsu.financeservice.api.dto.CreateAccountDto;
import com.devsu.financeservice.common.dto.ApiResponse;
import com.devsu.financeservice.domain.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(createSuccessResponse("Accounts retrieved successfully", accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Long id) {
        AccountDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(createSuccessResponse("Account retrieved successfully", account));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody CreateAccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return new ResponseEntity<>(createSuccessResponse("Account created successfully", createdAccount), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> updateAccount(@PathVariable Long id, @Valid @RequestBody CreateAccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccount(id, accountDto);
        return ResponseEntity.ok(createSuccessResponse("Account updated successfully", updatedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(createSuccessResponse("Account deleted successfully", null));
    }

    @GetMapping("/account-number/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(createSuccessResponse("Account retrieved successfully", accountDto));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAccountsByClientId(@PathVariable Long clientId) {
        List<AccountDto> accountDtos = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(createSuccessResponse("Accounts retrieved successfully", accountDtos));
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
