package com.sunhill.task.bankmanager.manager;

import com.sunhill.task.bankmanager.enums.BankAccountType;
import com.sunhill.task.bankmanager.exception.NotEnoughMoneyOnAccountException;
import com.sunhill.task.bankmanager.exception.OperationNotSupportedException;
import com.sunhill.task.bankmanager.model.CheckingBankAccount;
import com.sunhill.task.bankmanager.service.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Component
public class CheckingBankAccountManager extends BankAccountManager<CheckingBankAccount> {

    private static final Logger LOGGER = LogManager.getLogger(CheckingBankAccountManager.class);

    @Autowired
    public CheckingBankAccountManager(BankAccountService bankAccountService) {
        super(bankAccountService);
    }

    @Override
    public void interestPayment(CheckingBankAccount bankAccount) {
        throw new OperationNotSupportedException("CheckingBankAccountManager.interestPayment");
    }

    @Override
    public void transfer(CheckingBankAccount from, CheckingBankAccount to, BigDecimal amount) {
        Assert.notNull(from, "from BankAccount can not be null");
        Assert.notNull(to, "to BankAccount can not be null");
        Assert.notNull(amount, "amount can not be null");

        BigDecimal initialBalanceFrom = from.getBalance();
        BigDecimal initialBalanceTo = to.getBalance();
        try {
            transferInternal(from, to, amount);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            from.setBalance(initialBalanceFrom);
            to.setBalance(initialBalanceTo);
        }
    }

    @Override
    protected void withdrawInternal(CheckingBankAccount bankAccount, BigDecimal amount) {
        Assert.notNull(bankAccount, "BankAccount can not be null");
        Assert.notNull(amount, "Amount can not be null");

        BigDecimal balance = bankAccount.getBalance();
        BigDecimal newBalance = balance.add(bankAccount.getOverdraftLimit()).subtract(amount);

        verifyWithdrawal(bankAccount, newBalance);
        bankAccountService.subtractFromBalance(bankAccount, amount);
    }

    private void transferInternal(CheckingBankAccount from, CheckingBankAccount to, BigDecimal amount) {
        Assert.notNull(from, "from BankAccount can not be null");
        Assert.notNull(to, "to BankAccount can not be null");
        Assert.notNull(amount, "amount can not be null");

        verifyBankAccountType(from, BankAccountType.CHECKING);
        verifyBankAccountType(to, BankAccountType.CHECKING);

        withdrawInternal(from, amount);
        depositInternal(to, amount);
    }

    private void verifyWithdrawal(CheckingBankAccount bankAccount, BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyOnAccountException(bankAccount);
        }
    }
}
