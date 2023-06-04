// package com.cg.bankserver.transactionservice.services;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.cg.bankserver.transactionservice.dao.TransactionDAO;
//import com.cg.bankserver.transactionservice.entities.*;
//import com.cg.bankserver.transactionservice.exceptions.*;
//
//@Component(value="bankService")
//public class TransactionServiceImpl implements TransactionService {
//
//	@Autowired
//	private TransactionDAO transactionDao;
//
//	public static final double MINIMUM_BALANCE = 1000;
//
//	@Override
//	public Transaction saveTransaction(Transaction transaction) {
//		return transactionDao.save(transaction);
//	}
//
//	public List<Transaction> showLastTenTransactions(int accountNumber) throws InvalidAccountNumberException {
//		return transactionDao.findFirst10ByAccountNumberOrderByDateDesc(accountNumber);
//	}
//
//	public List<Transaction> showTransactionsBetweenDates(int accountNumber, LocalDateTime fromDate, LocalDateTime toDate) throws InvalidAccountNumberException{
//		return transactionDao.findByAccountNumberAndDateBetween(accountNumber, fromDate, toDate);
//	}
//
//	@Override
//	public List<Transaction> getAllTransactionDetails(int accountNumber) {
//		return transactionDao.findByAccountNumber(accountNumber);
//	}
//}
