package com.cg.bankserver.transactionservice.query.services;

import com.cg.bankserver.transactionservice.exceptions.BankingServicesException;
import com.cg.bankserver.transactionservice.exceptions.TransactionDetailsNotFoundException;
import com.cg.bankserver.transactionservice.query.entities.Transaction;

import java.util.List;

public interface TransactionQueryService {
    Transaction getTransactionById(int id) throws TransactionDetailsNotFoundException;
    List<Transaction> getTransactionByAccountNumber(int accNo) throws BankingServicesException;
}
