package com.sunhill.task.bankmanager.service;

import com.sunhill.task.bankmanager.model.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Override
    public void addToBalance(BankAccount bankAccount, BigDecimal amountToAdd) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        Assert.notNull(amountToAdd, "amountToAdd can not be null");

        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal newBalance = currentBalance.add(amountToAdd);

        bankAccount.setBalance(newBalance);
    }

    @Override
    public void subtractFromBalance(BankAccount bankAccount, BigDecimal amountToSubtract) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        Assert.notNull(amountToSubtract, "amountToSubtract can not be null");

        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amountToSubtract);

        bankAccount.setBalance(newBalance);
    }
}
