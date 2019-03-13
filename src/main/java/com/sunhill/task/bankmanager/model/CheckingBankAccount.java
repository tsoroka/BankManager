package com.sunhill.task.bankmanager.model;

import com.sunhill.task.bankmanager.enums.BankAccountType;

import java.math.BigDecimal;
import java.util.Objects;

public class CheckingBankAccount extends BankAccount {

    private BigDecimal overdraftLimit;

    public CheckingBankAccount(BigDecimal ballance, User owner, BigDecimal overdraftLimit) {
        super(ballance, owner);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public BankAccountType getType() {
        return BankAccountType.CHECKING;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckingBankAccount)) return false;
        if (!super.equals(o)) return false;
        CheckingBankAccount that = (CheckingBankAccount) o;
        return Objects.equals(overdraftLimit, that.overdraftLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overdraftLimit);
    }

    @Override
    public String toString() {
        return "CheckingBankAccount{" +
                "overdraftLimit=" + overdraftLimit +
                "} " + super.toString();
    }
}
