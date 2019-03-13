package com.sunhill.task.bankmanager.exception;

import com.sunhill.task.bankmanager.model.BankAccount;

public class NotEnoughMoneyOnAccountException extends RuntimeException {

    private BankAccount bankAccount;

    public NotEnoughMoneyOnAccountException(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String getMessage() {
        return String.format("Not enough money on account %s", bankAccount);
    }
}
