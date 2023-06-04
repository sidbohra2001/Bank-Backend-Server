package com.cg.bankserver.transactionservice.command.repositories;

import com.cg.bankserver.transactionservice.command.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCommandRepository extends JpaRepository<Transaction, Integer> {

}
