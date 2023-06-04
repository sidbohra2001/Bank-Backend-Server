package com.cg.bankserver.accountservice.services;

import com.cg.bankserver.accountservice.entities.Account;
import com.cg.bankserver.accountservice.exceptions.AccountCreationException;
import com.cg.bankserver.accountservice.exceptions.InsufficientBalanceException;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.exceptions.NegativeAmountException;
import com.cg.bankserver.accountservice.exceptions.TransactionFailedException;

public interface AccountService {
	Account createNewAccount(Account account) throws AccountCreationException ;
	public Account getAccountDetails(int accountNo)throws AccountDetailsNotFoundException;
	boolean validateAccountNumber(int accountNumber);
	double deposit(int accountNumber, double amount) throws AccountDetailsNotFoundException, NegativeAmountException;
	double withdraw(int accountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException;
	double fundTransfer(int fromAccountNumber, int toAccountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException, TransactionFailedException;
	double checkBalance(int accountNumber) throws AccountDetailsNotFoundException;
}
