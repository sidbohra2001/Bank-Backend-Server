package com.cg.bankserver.accountservice.command.services;

import com.cg.bankserver.accountservice.command.entities.Account;
import com.cg.bankserver.accountservice.exceptions.AccountCreationException;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.exceptions.BankingServicesException;
import com.cg.bankserver.accountservice.exceptions.InsufficientBalanceException;
import com.cg.bankserver.accountservice.exceptions.NegativeAmountException;
import com.cg.bankserver.accountservice.exceptions.TransactionFailedException;

public interface AccountCommandService {
    Account createAccount(Account accountData) throws  AccountCreationException, InsufficientBalanceException, BankingServicesException;
    double deposit(int accountNumber, double amount) throws AccountDetailsNotFoundException, NegativeAmountException, BankingServicesException;
    double withdraw(int accountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException, BankingServicesException;
    double fundTransfer(int fromAccountNumber, int toAccountNumber, double amount) throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException, TransactionFailedException, BankingServicesException;
}
