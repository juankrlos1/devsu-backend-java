package com.devsu.financeservice.infrastructure.mapper;

import com.devsu.financeservice.api.dto.TransactionDto;
import com.devsu.financeservice.api.dto.CreateTransactionDto;
import com.devsu.financeservice.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "account.accountNumber", source = "account.accountNumber")
    TransactionDto toDto(Transaction transaction);

    @Mapping(target = "account.accountNumber", source = "account.accountNumber")
    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(target = "account.accountNumber", source = "accountNumber")
    Transaction toEntity(CreateTransactionDto createTransactionDto);
}
