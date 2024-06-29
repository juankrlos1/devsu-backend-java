package com.devsu.financeservice.infrastructure.mapper;

import com.devsu.financeservice.api.dto.AccountDto;
import com.devsu.financeservice.api.dto.CreateAccountDto;
import com.devsu.financeservice.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "clientId", source = "clientId")
    AccountDto toDto(Account account);

    @Mapping(target = "clientId", source = "clientId")
    Account toEntity(CreateAccountDto createAccountDto);

    void updateEntityFromDto(CreateAccountDto createAccountDto, @MappingTarget Account account);
}
