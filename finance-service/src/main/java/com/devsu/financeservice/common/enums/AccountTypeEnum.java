package com.devsu.financeservice.common.enums;

public enum AccountTypeEnum {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private final String displayName;

    AccountTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}