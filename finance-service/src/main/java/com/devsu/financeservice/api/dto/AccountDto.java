package com.devsu.financeservice.api.dto;

import com.devsu.financeservice.common.enums.AccountTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Long accountId;
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private Boolean status;
    private Long clientId;
}
