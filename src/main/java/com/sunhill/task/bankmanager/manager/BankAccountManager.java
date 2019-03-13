package com.sunhill.task.bankmanager.manager;

import com.sunhill.task.bankmanager.enums.BankAccountType;
import com.sunhill.task.bankmanager.exception.UnexpectedBankAccountTypeException;
import com.sunhill.task.bankmanager.model.BankAccount;
import com.sunhill.task.bankmanager.model.SavingBankAccount;
import com.sunhill.task.bankmanager.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BankAccountManager <T extends BankAccount> {

    private static final Logger LOGGER = LogManager.getLogger(BankAccountManager.class);

    protected BankAccountService bankAccountService;

    public BankAccountManager(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    protected abstract void withdrawInternal(T bankAccount, BigDecimal amount);

    public abstract void interestPayment(T bankAccount);

    public abstract void transfer(T from, T to, BigDecimal amount);

    public void withdraw(T bankAccount, BigDecimal amount) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        Assert.notNull(amount, "Amount can not be null");

        BigDecimal initialBalance = bankAccount.getBalance();
        try {
            this.withdrawInternal(bankAccount, amount);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            bankAccount.setBalance(initialBalance);
        }
    }

    public void deposit(T bankAccount, BigDecimal amount) {
        Objects.requireNonNull(bankAccount, "BankAccount can not be null");
        Objects.requireNonNull(amount, "Amount can not be null");

        BigDecimal initialBalance = bankAccount.getBalance();
        try {
            this.depositInternal(bankAccount, amount);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            bankAccount.setBalance(initialBalance);
        }
    }

    protected void depositInternal(T bankAccount, BigDecimal amount) {
        Objects.requireNonNull(bankAccount, "BankAccount can not be null");
        Objects.requireNonNull(amount, "Amount can not be null");

        bankAccountService.addToBalance(bankAccount, amount);
    }

    protected void verifyBankAccountType(BankAccount from, BankAccountType expectedBankAccountType) {
        if (from.getType() != expectedBankAccountType) {
            throw new UnexpectedBankAccountTypeException(from.getType(), expectedBankAccountType);
        }
    }
}
