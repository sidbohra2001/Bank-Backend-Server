package com.cg.bankserver.transactionservice.query.projection;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.bankserver.transactionservice.exceptions.TransactionDetailsNotFoundException;
import com.cg.bankserver.transactionservice.query.GetTransactionByIdQuery;
import com.cg.bankserver.transactionservice.query.GetTransactionsByAccountNumberQuery;
import com.cg.bankserver.transactionservice.query.entities.Transaction;
import com.cg.bankserver.transactionservice.query.repository.TransactionQueryRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionProjection {
    @Autowired
    private TransactionQueryRepository repository;

    @QueryHandler
    public Optional<Transaction> handle(GetTransactionByIdQuery getTransactionByIdQuery) throws TransactionDetailsNotFoundException {
        log.info("finding customer : " + getTransactionByIdQuery.toString());
        return repository.findById(getTransactionByIdQuery.getId());
    }

    @QueryHandler
    public List<Transaction> handle(GetTransactionsByAccountNumberQuery getTransactionsByAccountNumberQuery) {
        return repository.findByAccountNumber(getTransactionsByAccountNumberQuery.getAccountNumber());
    }
}
