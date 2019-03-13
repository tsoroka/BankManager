package com.sunhill.task.bankmanager.service;

import com.sunhill.task.bankmanager.model.CheckingBankAccount;
import com.sunhill.task.bankmanager.model.User;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankAccountServiceImplTest {

    private BankAccountService bankAccountService = new BankAccountServiceImpl();

    private User user;

    @Before
    public void setUp() {
        user = new User("TestUser1", "Ukraine");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithAmountToAddNull() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountService.addToBalance(checkingBankAccount, null);
    }

    @Test
    public void testAddToBalance() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountService.addToBalance(checkingBankAccount, BigDecimal.valueOf(30.0));

        assertEquals(BigDecimal.valueOf(90.5), checkingBankAccount.getBalance());
    }

    @Test
    public void testSubtractFromBalance() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        BigDecimal overdraftLimit = BigDecimal.valueOf(10.0);

        CheckingBankAccount checkingBankAccount = new CheckingBankAccount(balance, user, overdraftLimit);

        bankAccountService.subtractFromBalance(checkingBankAccount, BigDecimal.valueOf(30.0));

        assertEquals(BigDecimal.valueOf(30.5), checkingBankAccount.getBalance());
    }
}