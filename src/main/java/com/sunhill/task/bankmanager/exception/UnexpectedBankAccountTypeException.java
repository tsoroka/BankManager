package com.sunhill.task.bankmanager.exception;

import com.sunhill.task.bankmanager.enums.BankAccountType;

public class UnexpectedBankAccountTypeException extends RuntimeException {

    private BankAccountType accountType;

    private BankAccountType expectedAccountType;

    public UnexpectedBankAccountTypeException(BankAccountType accountType, BankAccountType expectedAccountType) {
        this.accountType = accountType;
        this.expectedAccountType = expectedAccountType;
    }

    @Override
    public String getMessage() {
        return String.format("Incorrect account type: %s, expected: %s", accountType, expectedAccountType);
    }
}
