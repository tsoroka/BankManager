package com.sunhill.task.bankmanager.model;

import com.sunhill.task.bankmanager.enums.BankAccountType;

import java.math.BigDecimal;
import java.util.Objects;

public class SavingBankAccount extends BankAccount {

    private Double interestRate;

    @Override
    public BankAccountType getType() {
        return BankAccountType.SAVING;
    }

    public SavingBankAccount(BigDecimal balance, User owner, Double interestRate) {
        super(balance, owner);
        this.interestRate = interestRate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingBankAccount)) return false;
        if (!super.equals(o)) return false;
        SavingBankAccount that = (SavingBankAccount) o;
        return Objects.equals(interestRate, that.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), interestRate);
    }

    @Override
    public String toString() {
        return "SavingBankAccount{" +
                "interestRate=" + interestRate +
                "} " + super.toString();
    }
}
