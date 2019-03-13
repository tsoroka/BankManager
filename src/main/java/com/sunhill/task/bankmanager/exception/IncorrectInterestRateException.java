package com.sunhill.task.bankmanager.exception;

import com.sunhill.task.bankmanager.model.BankAccount;

public class IncorrectInterestRateException extends RuntimeException {

    private BankAccount bankAccount;

    public IncorrectInterestRateException(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String getMessage() {
        return String.format("Incorrect interest rate for account %s", bankAccount);
    }
}
