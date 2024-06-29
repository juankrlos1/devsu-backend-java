package com.devsu.financeservice.api.controller;

import com.devsu.financeservice.api.dto.AccountStatementDto;
import com.devsu.financeservice.common.dto.ApiResponse;
import com.devsu.financeservice.domain.service.ReportService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountStatementDto>>> getAccountReport(
            @RequestParam Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<AccountStatementDto> report = reportService.getAccountStatements(clientId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        return ResponseEntity.ok(createSuccessResponse(report));
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<AccountStatementDto>> getAccountStatementByAccountNumber(
            @RequestParam @NotNull String accountNumber,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        AccountStatementDto statement = reportService.getAccountStatementByAccountNumber(accountNumber, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());

        return ResponseEntity.ok(createSuccessResponse(statement));
    }

    private <T> ApiResponse<T> createSuccessResponse(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Report generated successfully")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
