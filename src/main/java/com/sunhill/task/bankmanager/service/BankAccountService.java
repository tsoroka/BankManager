package com.sunhill.task.bankmanager.service;

import com.sunhill.task.bankmanager.model.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {

    void addToBalance(BankAccount bankAccount, BigDecimal amountToAdd);

    void subtractFromBalance(BankAccount bankAccount, BigDecimal amountToAdd);


}
