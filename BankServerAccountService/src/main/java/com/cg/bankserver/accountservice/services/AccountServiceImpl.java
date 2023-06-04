 package com.cg.bankserver.accountservice.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.bankserver.accountservice.dao.AccountDAO;
import com.cg.bankserver.accountservice.dto.TransactionDTO;
import com.cg.bankserver.accountservice.dto.TransactionType;
import com.cg.bankserver.accountservice.entities.*;
import com.cg.bankserver.accountservice.exceptions.*;

import org.springframework.web.reactive.function.client.WebClient;

 @Component(value="bankService")
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDAO bankDao;
	
	@Autowired
	private WebClient.Builder builder;
	
	public static final double MINIMUM_BALANCE = 1000;
	
	
	@Override
	public Account createNewAccount(Account account) {
		return bankDao.save(account);
	}

	@Override
	public boolean validateAccountNumber(int accountNumber) {
		return bankDao.existsById(accountNumber);
	}
	
	@Override
	public Account getAccountDetails(int accountNo) throws AccountDetailsNotFoundException {
		return bankDao.findById(accountNo).orElseThrow(()->new AccountDetailsNotFoundException("Account Details Not found for accountNo :- "+accountNo));
	}

	public double deposit(int accountNumber, double amount) throws AccountDetailsNotFoundException, NegativeAmountException {
		if (amount < 0) 
			throw new NegativeAmountException("Amount entered is negative :- " + amount);
		Account account = bankDao.findById(accountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException("Could not find account with account number :- " + accountNumber));
		
		
		double nBalance = account.getBalance() + amount;
		TransactionDTO transaction = new TransactionDTO(0, TransactionType.CREDIT,
									amount, 
									nBalance, 
									LocalDateTime.now(), 
									account.getAccountNumber());
		saveTransaction(transaction);
		
		account.setBalance(nBalance);
		
		return bankDao.save(account).getBalance();

	}

	public double withdraw(int accountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException {
		if (amount < 0) 
			throw new NegativeAmountException("Amount entered is negative :- " + amount);
		
		Account account = bankDao.findById(accountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException("Could not find account with account number :- " + accountNumber));
		
		double balance = account.getBalance();
		
		if (balance - amount < MINIMUM_BALANCE) 
			throw new InsufficientBalanceException("Insufficient Balance : Withdraw amount :- " + amount + " Available Balance :- " + balance);
		
		double nBalance = account.getBalance() - amount;
		TransactionDTO transaction = new TransactionDTO(0, TransactionType.DEBIT, 
									amount, 
									nBalance, 
									LocalDateTime.now(), 
									account.getAccountNumber());
		
		saveTransaction(transaction);
		account.setBalance(nBalance);
		return bankDao.save(account).getBalance();
	}

	public double checkBalance(int accountNumber) throws AccountDetailsNotFoundException {
		return bankDao.findById(accountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException("Could not find account with account number :- " + accountNumber))
				.getBalance();
	}

	public double fundTransfer(int fromAccountNumber, int toAccountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException, TransactionFailedException {
		if (fromAccountNumber == toAccountNumber)
			throw new AccountDetailsNotFoundException("Sender and receiver account number cannot be same");
		if (amount < 0) 
			throw new NegativeAmountException("Amount entered is negative :- " + amount);
		
		Account fromAccount = bankDao.findById(fromAccountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException("Could not find account with account number :- " + fromAccountNumber));
		
		Account toAccount = bankDao.findById(toAccountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException("Could not find account with account number :- " + toAccountNumber));
		
		double fromBalance = fromAccount.getBalance();
		
		if (fromBalance - amount < MINIMUM_BALANCE) 
			throw new InsufficientBalanceException("Insufficient Balance : Transfer Amount :- " + amount + " Available Balance :- " + fromBalance);
		
		double nFromBalance = fromBalance - amount;
		TransactionDTO fromTransaction = new TransactionDTO(0, TransactionType.DEBIT, 
										amount, 
										nFromBalance, 
										LocalDateTime.now(), 
										fromAccount.getAccountNumber());

		saveTransaction(fromTransaction);
		
		fromAccount.setBalance(nFromBalance);
		Account updatedFromAccount = bankDao.save(fromAccount);
		
		double toBalance = toAccount.getBalance();
		double nToBalance = toBalance + amount;

		TransactionDTO toTransaction = new TransactionDTO(0, TransactionType.CREDIT, 
										amount, 
										nToBalance, 
										LocalDateTime.now(), 
										 toAccount.getAccountNumber());

		saveTransaction(toTransaction);

		toAccount.setBalance(nToBalance);
		bankDao.save(toAccount);
		return updatedFromAccount.getBalance();	
	}

	private TransactionDTO saveTransaction(TransactionDTO transaction) {
		var client = builder
						.baseUrl("http://BANK-SERVER-TRANSACTION-SERVICE")
						.build();
		return client.post()
				.uri("/transaction/save")
				.bodyValue(transaction)
				.retrieve()
				.bodyToMono(TransactionDTO.class)
				.block();
	}

}
