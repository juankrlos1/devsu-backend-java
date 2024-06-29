package com.devsu.financeservice.common.enums;

public enum TransactionTypeEnum {
    DEPOSITO("Deposito"),
    RETIRO("Retiro");

    private final String displayName;

    TransactionTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
