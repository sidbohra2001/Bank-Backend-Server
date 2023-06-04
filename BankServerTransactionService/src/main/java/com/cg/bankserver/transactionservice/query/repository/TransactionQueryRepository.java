package com.cg.bankserver.transactionservice.query.repository;

import com.cg.bankserver.transactionservice.query.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionQueryRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccountNumber(int accountNumber);
}
