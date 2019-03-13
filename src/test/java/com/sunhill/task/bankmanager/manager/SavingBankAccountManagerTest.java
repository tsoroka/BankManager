package com.sunhill.task.bankmanager.manager;

import com.sunhill.task.bankmanager.exception.OperationNotSupportedException;
import com.sunhill.task.bankmanager.model.SavingBankAccount;
import com.sunhill.task.bankmanager.model.User;
import com.sunhill.task.bankmanager.service.BankAccountService;
import com.sunhill.task.bankmanager.service.BankAccountServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class SavingBankAccountManagerTest {

    private BankAccountManager<SavingBankAccount> bankAccountManager;

    private User user;

    @Before
    public void setUp() {
        BankAccountService bankAccountService = new BankAccountServiceImpl();
        bankAccountManager = new SavingBankAccountManager(bankAccountService);

        user = new User("TestUser1", "Ukraine");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawWithAccountNull() {
        bankAccountManager.withdraw(null, BigDecimal.TEN);
    }

    @Test
    public void testWithdrawOk() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        SavingBankAccount savingBankAccount = new SavingBankAccount(balance, user, 5.5);

        bankAccountManager.withdraw(savingBankAccount, BigDecimal.TEN);

        assertEquals(BigDecimal.valueOf(50.5), savingBankAccount.getBalance());
    }

    @Test
    public void testWithdrawNotOk() {
        BigDecimal balance = BigDecimal.valueOf(60.5);
        SavingBankAccount savingBankAccount = new SavingBankAccount(balance, user, 5.5);

        bankAccountManager.withdraw(savingBankAccount, BigDecimal.valueOf(70));

        assertEquals(BigDecimal.valueOf(60.5), savingBankAccount.getBalance());
    }

    @Test
    public void testInterestPayment() {
        BigDecimal balance = BigDecimal.valueOf(60);
        SavingBankAccount savingBankAccount = new SavingBankAccount(balance, user, 5.0);

        bankAccountManager.interestPayment(savingBankAccount);

        BigDecimal expectedBalance = BigDecimal.valueOf(63.0).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expectedBalance, savingBankAccount.getBalance());
    }


    @Test
    public void testInterestPaymentWithNotCorrectInterest() {
        BigDecimal balance = BigDecimal.valueOf(60);
        SavingBankAccount savingBankAccount = new SavingBankAccount(balance, user, 0.0);

        bankAccountManager.interestPayment(savingBankAccount);

        assertEquals(balance, savingBankAccount.getBalance());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testTransfer() {
        BigDecimal balance = BigDecimal.valueOf(60);
        SavingBankAccount savingBankAccountFrom = new SavingBankAccount(balance, user, 0.0);
        SavingBankAccount savingBankAccountTo = new SavingBankAccount(balance, user, 0.0);

        bankAccountManager.transfer(savingBankAccountFrom, savingBankAccountTo, BigDecimal.valueOf(10));
    }

    @Test
    public void testDepositOk() {
        BigDecimal balance = BigDecimal.valueOf(60);
        SavingBankAccount savingBankAccount = new SavingBankAccount(balance, user, 5.0);

        bankAccountManager.deposit(savingBankAccount, BigDecimal.valueOf(10));

        assertEquals(BigDecimal.valueOf(70), savingBankAccount.getBalance());
    }
}