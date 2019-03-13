package com.sunhill.task.bankmanager.manager;

import com.sunhill.task.bankmanager.enums.BankAccountType;
import com.sunhill.task.bankmanager.exception.IncorrectInterestRateException;
import com.sunhill.task.bankmanager.exception.NotEnoughMoneyOnAccountException;
import com.sunhill.task.bankmanager.exception.OperationNotSupportedException;
import com.sunhill.task.bankmanager.model.BankAccount;
import com.sunhill.task.bankmanager.model.SavingBankAccount;
import com.sunhill.task.bankmanager.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class SavingBankAccountManager extends BankAccountManager<SavingBankAccount> {

    private static final Logger LOGGER = LogManager.getLogger(SavingBankAccountManager.class);

    @Autowired
    public SavingBankAccountManager(BankAccountService bankAccountService) {
        super(bankAccountService);
    }

    @Override
    public void interestPayment(SavingBankAccount bankAccount) {
        Assert.notNull(bankAccount, "BankAccount can not be null");

        BigDecimal initialBalance = bankAccount.getBalance();
        try {
            interestPaymentInternal(bankAccount);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            bankAccount.setBalance(initialBalance);
        }
    }

    @Override
    public void transfer(SavingBankAccount from, SavingBankAccount to, BigDecimal amount) {
        throw new OperationNotSupportedException("SavingBankAccountManager.transfer");
    }

    @Override
    protected void withdrawInternal(SavingBankAccount bankAccount, BigDecimal amount) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        Assert.notNull(amount, "Amount can not be null");

        BigDecimal balance = bankAccount.getBalance();
        BigDecimal balanceAfterWithdrawal = balance.subtract(amount);

        verifyWithdrawal(bankAccount, balance, balanceAfterWithdrawal);
        bankAccountService.subtractFromBalance(bankAccount, amount);
    }

    private void interestPaymentInternal(SavingBankAccount bankAccount) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        verifyBankAccountType(bankAccount, BankAccountType.SAVING);

        Double interestRate = bankAccount.getInterestRate();
        if (interestRate == 0) {
            throw new IncorrectInterestRateException(bankAccount);
        }
        BigDecimal newBalance = bankAccount.getBalance().multiply(BigDecimal.valueOf(interestRate).movePointLeft(2));
        newBalance = newBalance.setScale(2, RoundingMode.HALF_UP);

        bankAccountService.addToBalance(bankAccount, newBalance);
    }

    private void verifyWithdrawal(BankAccount bankAccount, BigDecimal balance, BigDecimal balanceAfterWithdrawal) {
        if (balanceAfterWithdrawal.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyOnAccountException(bankAccount);
        }
    }
}
