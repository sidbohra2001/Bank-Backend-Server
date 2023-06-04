package com.cg.bankserver.transactionservice.command.service;

import com.cg.bankserver.transactionservice.command.entities.Transaction;

import java.util.List;

public interface TransactionCommandService {
    public Transaction saveTransaction(Transaction transaction);
}
