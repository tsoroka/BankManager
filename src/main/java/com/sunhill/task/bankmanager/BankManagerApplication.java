package com.sunhill.task.bankmanager;

import com.sunhill.task.bankmanager.manager.BankAccountManager;
import com.sunhill.task.bankmanager.model.CheckingBankAccount;
import com.sunhill.task.bankmanager.model.SavingBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankManagerApplication {

	@Autowired
	@Qualifier("checkingBankAccountManager")
	private BankAccountManager<CheckingBankAccount> checkingBankAccountManager;

	@Autowired
	@Qualifier("savingBankAccountManager")
	private BankAccountManager<SavingBankAccount> savingBankAccountManager;

	public static void main(String[] args) {
		SpringApplication.run(BankManagerApplication.class, args);
	}

}
