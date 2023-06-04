package com.cg.bankserver.transactionservice.command.service;

import com.cg.bankserver.transactionservice.command.TransactionCreateCommand;
import com.cg.bankserver.transactionservice.command.repositories.TransactionCommandRepository;
import com.cg.bankserver.transactionservice.command.entities.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class TransactionCommandServiceImpl implements TransactionCommandService {

    @Autowired private CommandGateway commandGateway;

    @Autowired private TransactionCommandRepository repository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        transaction = repository.save(transaction);
        TransactionCreateCommand createCommand = TransactionCreateCommand.builder()
                .uid(UUID.randomUUID().toString())
                .id(transaction.getId())
                .date(transaction.getDate())
                .balance(transaction.getBalance())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .accountNumber(transaction.getAccountNumber())
                .build();
        log.info("create command created :- " + createCommand);
        commandGateway.sendAndWait(createCommand);
        return transaction;
    }
}
