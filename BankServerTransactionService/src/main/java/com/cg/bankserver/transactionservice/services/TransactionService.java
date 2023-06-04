package com.cg.bankserver.transactionservice.services;

import java.time.LocalDateTime;
import java.util.List;

import com.cg.bankserver.transactionservice.entities.*;
import com.cg.bankserver.transactionservice.exceptions.*;

public interface TransactionService {
	Transaction saveTransaction(Transaction transaction);
	List<Transaction> getAllTransactionDetails(int accountNumber);
	List<Transaction> showLastTenTransactions(int accountNumber) throws InvalidAccountNumberException;
	List<Transaction> showTransactionsBetweenDates(int accountNumber, LocalDateTime fromDate, LocalDateTime toDate) throws InvalidAccountNumberException;
}
