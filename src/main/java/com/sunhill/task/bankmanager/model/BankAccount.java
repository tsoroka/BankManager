package com.sunhill.task.bankmanager.model;

import com.sunhill.task.bankmanager.enums.BankAccountType;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BankAccount {

    private BigDecimal balance;

    private User owner;

    public abstract BankAccountType getType();

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BankAccount(BigDecimal balance, User owner) {
        this.balance = balance;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(balance, that.balance) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, owner);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}
