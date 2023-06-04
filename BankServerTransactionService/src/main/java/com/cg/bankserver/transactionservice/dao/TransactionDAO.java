package com.cg.bankserver.transactionservice.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bankserver.transactionservice.entities.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByAccountNumber(int accountNumber);
	List<Transaction> findFirst10ByAccountNumberOrderByDateDesc(int accountNumber);
	List<Transaction> findByAccountNumberAndDateBetween(int accountNumber, LocalDateTime startDate, LocalDateTime endDate);
}
