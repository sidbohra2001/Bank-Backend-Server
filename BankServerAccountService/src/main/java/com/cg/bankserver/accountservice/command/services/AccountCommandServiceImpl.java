package com.cg.bankserver.accountservice.command.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cg.bankserver.accountservice.command.AccountCreateCommand;
import com.cg.bankserver.accountservice.command.AccountUpdateCommand;
import com.cg.bankserver.accountservice.command.TransactionEntryFailedCommand;
import com.cg.bankserver.accountservice.command.entities.Account;
import com.cg.bankserver.accountservice.command.repository.AccountCommandRepository;
import com.cg.bankserver.accountservice.dto.CustomerDTO;
import com.cg.bankserver.accountservice.dto.TransactionDTO;
import com.cg.bankserver.accountservice.dto.TransactionType;
import com.cg.bankserver.accountservice.exceptions.AccountCreationException;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.exceptions.BankingServicesException;
import com.cg.bankserver.accountservice.exceptions.InsufficientBalanceException;
import com.cg.bankserver.accountservice.exceptions.NegativeAmountException;
import com.cg.bankserver.accountservice.exceptions.TransactionFailedException;
import com.cg.bankserver.accountservice.query.services.AccountQueryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccountCommandServiceImpl implements AccountCommandService {
	@Autowired
	private AccountCommandRepository repository;

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private AccountQueryService queryService;

	@Autowired
	private WebClient.Builder builder;

	public static final double MINIMUM_BALANCE = 1000;

	@Override
	public Account createAccount(Account account)
			throws InsufficientBalanceException, BankingServicesException, AccountCreationException {
		if (account.getBalance() < MINIMUM_BALANCE)
			throw new InsufficientBalanceException("Balance cannot be less than 1000.0");
		if (checkCustomer(account.getCustomerId()) == false)
			throw new AccountCreationException("Customer Id " + account.getCustomerId() + " is invalid.");
		account = repository.save(account);
		AccountCreateCommand accountCreateCommandCommand = AccountCreateCommand.builder()
				.uid(UUID.randomUUID().toString()).accountNumber(account.getAccountNumber())
				.balance(account.getBalance()).customerId(account.getCustomerId()).build();
		commandGateway.sendAndWait(accountCreateCommandCommand);
		log.info("create command created :- " + accountCreateCommandCommand);
		return account;
	}

	public double deposit(int accountNumber, double amount)
			throws AccountDetailsNotFoundException, NegativeAmountException, BankingServicesException {
//        queryService.getAccountByAccountNumber(accountNumber);
		if (amount < 0)
			throw new NegativeAmountException("Amount entered is negative :- " + amount);
		Account account = repository.findById(accountNumber).orElseThrow(() -> new AccountDetailsNotFoundException(
				"Could not find account with account number :- " + accountNumber));

		double nBalance = account.getBalance() + amount;
		account.setBalance(nBalance);

		account = repository.save(account);
		TransactionDTO transaction = new TransactionDTO(0, TransactionType.CREDIT, amount, nBalance,
				LocalDateTime.now(), account.getAccountNumber());
		saveTransaction(transaction);
		updateAccountCommand(account, transaction);

		return account.getBalance();

	}

	public double withdraw(int accountNumber, double amount) throws AccountDetailsNotFoundException,
			InsufficientBalanceException, NegativeAmountException, BankingServicesException {
//        queryService.getAccountByAccountNumber(accountNumber);
		if (amount < 0)
			throw new NegativeAmountException("Amount entered is negative :- " + amount);

		Account account = repository.findById(accountNumber).orElseThrow(() -> new AccountDetailsNotFoundException(
				"Could not find account with account number :- " + accountNumber));

		double balance = account.getBalance();

		if (balance - amount < MINIMUM_BALANCE)
			throw new InsufficientBalanceException(
					"Insufficient Balance : Withdraw amount :- " + amount + " Available Balance :- " + balance);

		double nBalance = account.getBalance() - amount;
		account.setBalance(nBalance);
		account = repository.save(account);
		TransactionDTO transaction = new TransactionDTO(0, TransactionType.DEBIT, amount, nBalance, LocalDateTime.now(),
				account.getAccountNumber());

		saveTransaction(transaction);
		updateAccountCommand(account, transaction);
		return account.getBalance();
	}

	public double fundTransfer(int fromAccountNumber, int toAccountNumber, double amount)
			throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException,
			TransactionFailedException, BankingServicesException {
		if (fromAccountNumber == toAccountNumber)
			throw new AccountDetailsNotFoundException("Sender and receiver account number cannot be same");
		if (amount < 0)
			throw new NegativeAmountException("Amount entered is negative :- " + amount);

		Account fromAccount = repository.findById(fromAccountNumber)
				.orElseThrow(() -> new AccountDetailsNotFoundException(
						"Could not find account with account number :- " + fromAccountNumber));

		Account toAccount = repository.findById(toAccountNumber).orElseThrow(() -> new AccountDetailsNotFoundException(
				"Could not find account with account number :- " + toAccountNumber));

		double fromBalance = fromAccount.getBalance();

		if (fromBalance - amount < MINIMUM_BALANCE)
			throw new InsufficientBalanceException(
					"Insufficient Balance : Transfer Amount :- " + amount + " Available Balance :- " + fromBalance);

		double nFromBalance = fromBalance - amount;
		TransactionDTO fromTransaction = new TransactionDTO(0, TransactionType.DEBIT, amount, nFromBalance,
				LocalDateTime.now(), fromAccount.getAccountNumber());

		saveTransaction(fromTransaction);

		fromAccount.setBalance(nFromBalance);
		Account updatedFromAccount = repository.save(fromAccount);
		updateAccountCommand(updatedFromAccount, fromTransaction);

		double toBalance = toAccount.getBalance();
		double nToBalance = toBalance + amount;

		TransactionDTO toTransaction = new TransactionDTO(0, TransactionType.CREDIT, amount, nToBalance,
				LocalDateTime.now(), toAccount.getAccountNumber());

		saveTransaction(toTransaction);

		toAccount.setBalance(nToBalance);
		Account updatedToAccount = repository.save(toAccount);
		updateAccountCommand(updatedToAccount, fromTransaction);
		return updatedFromAccount.getBalance();
	}

	private void updateAccountCommand(Account account, TransactionDTO transaction) {
		AccountUpdateCommand updateCommand = AccountUpdateCommand.builder().uid(UUID.randomUUID().toString())
				.accountNumber(account.getAccountNumber()).balance(account.getBalance())
				.customerId(account.getCustomerId()).transactionType(transaction.getTransactionType())
				.amount(transaction.getAmount()).date(transaction.getDate()).build();
		commandGateway.sendAndWait(updateCommand);
		log.info("update command created :- " + updateCommand);
	}

	private TransactionDTO saveTransaction(TransactionDTO transaction) throws BankingServicesException {
		try {
			var client = builder.baseUrl("http://BANK-SERVER-TRANSACTION-SERVICE").build();
			return client.post().uri("/transaction/save").bodyValue(transaction).retrieve()
					.bodyToMono(TransactionDTO.class).block();
		} catch (Exception e) {
			var entryFailedCommand = TransactionEntryFailedCommand.builder().uid(UUID.randomUUID().toString())
					.date(transaction.getDate()).type(transaction.getTransactionType())
					.accountNumber(transaction.getAccountNumber()).amount(transaction.getAmount()).build();
			commandGateway.sendAndWait(entryFailedCommand);
			throw new BankingServicesException("Transaction Services are down.", e.getCause());
		}
	}

	private boolean checkCustomer(int customerId) throws BankingServicesException {
		try {
			var client = builder.baseUrl("http://BANK-SERVER-CUSTOMER-SERVICE").build();
			log.error(client.get().uri("/customer/getCustomer?userid=" + customerId).accept(MediaType.APPLICATION_JSON)
					.retrieve().bodyToMono(CustomerDTO.class).block().toString());
			if (client.get().uri("/customer/getCustomer?userid=" + customerId).accept(MediaType.APPLICATION_JSON)
					.retrieve().bodyToMono(CustomerDTO.class).block() != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new BankingServicesException("Customer with customer id : "+customerId+" does not exist.", e.getCause());
		}
	}

}
