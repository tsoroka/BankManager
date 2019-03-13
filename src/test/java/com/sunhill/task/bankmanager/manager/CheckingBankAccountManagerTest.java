package com.sunhill.task.bankmanager.manager;

import com.sunhill.task.bankmanager.exception.OperationNotSupportedException;
import com.sunhill.task.bankmanager.model.CheckingBankAccount;
import com.sunhill.task.bankmanager.model.User;
import com.sunhill.task.bankmanager.service.BankAccountService;
import com.sunhill.task.bankmanager.service.BankAccountServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CheckingBankAccountManagerTest {

    private BankAccountManager<CheckingBankAccount> bankAccountManager;

    private User user;

    @Before
    public void setUp() {
        BankAccountService bankAccountService = new BankAccountServiceImpl();
        bankAccountManager = new CheckingBankAccountManager(bankAccountService);

        user = new User("TestUser1", "Ukraine");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawWithAccountNull() {
        bankAccountManager.withdraw(null, BigDecimal.TEN);
    }

    @Test
    public void testWithdrawOk() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountManager.withdraw(checkingBankAccount, BigDecimal.TEN);

        assertEquals(BigDecimal.valueOf(50.5), checkingBankAccount.getBalance());
    }

    @Test
    public void testWithdrawWithOverdraftOk() {
        BigDecimal balance = BigDecimal.valueOf(60);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountManager.withdraw(checkingBankAccount, BigDecimal.valueOf(65));

        assertEquals(BigDecimal.valueOf(-5), checkingBankAccount.getBalance());
    }

    @Test
    public void testWithdrawWithOverdraftNotOk() {
        BigDecimal initialBalance = BigDecimal.valueOf(60);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(initialBalance, user, overdraftLimit);

        bankAccountManager.withdraw(checkingBankAccount, BigDecimal.valueOf(75));

        assertEquals(initialBalance, checkingBankAccount.getBalance());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void interestPayment() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountManager.interestPayment(checkingBankAccount);
    }

    @Test
    public void testTransferOk() {
        BigDecimal balanceFrom = BigDecimal.valueOf(60);
        BigDecimal overdraftLimitFrom = BigDecimal.valueOf(10);

        CheckingBankAccount from = new CheckingBankAccount(balanceFrom, user, overdraftLimitFrom);

        BigDecimal balanceTo = BigDecimal.valueOf(30);
        BigDecimal overdraftLimitTo = BigDecimal.valueOf(10);

        CheckingBankAccount to = new CheckingBankAccount(balanceTo, user, overdraftLimitTo);

        bankAccountManager.transfer(from, to, BigDecimal.valueOf(10.0));

        assertEquals(BigDecimal.valueOf(50.0), from.getBalance());
        assertEquals(BigDecimal.valueOf(40.0), to.getBalance());
    }

    @Test
    public void testTransferNotOk() {
        BigDecimal balanceFrom = BigDecimal.valueOf(60);
        BigDecimal overdraftLimitFrom = BigDecimal.valueOf(10);

        CheckingBankAccount from = new CheckingBankAccount(balanceFrom, user, overdraftLimitFrom);

        BigDecimal balanceTo = BigDecimal.valueOf(30);
        BigDecimal overdraftLimitTo = BigDecimal.valueOf(10);

        CheckingBankAccount to = new CheckingBankAccount(balanceTo, user, overdraftLimitTo);

        bankAccountManager.transfer(from, to, BigDecimal.valueOf(80.0));

        assertEquals(balanceFrom, from.getBalance());
        assertEquals(balanceTo, to.getBalance());
    }

    @Test
    public void testDepositOk() {
        BigDecimal balance = BigDecimal.valueOf(60.0);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountManager.deposit(checkingBankAccount, BigDecimal.valueOf(10));

        assertEquals(BigDecimal.valueOf(70.0), checkingBankAccount.getBalance());
    }
}