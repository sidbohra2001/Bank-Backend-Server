package com.cg.bankserver.transactionservice.command.aggregate;

import com.cg.bankserver.transactionservice.command.TransactionCreateCommand;
import com.cg.bankserver.transactionservice.command.events.TransactionCreateEvent;
import com.cg.bankserver.transactionservice.entities.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Aggregate
@Slf4j
public class TransactionAggregate {
    @AggregateIdentifier
    private String uid;
    private int id;
    private TransactionType transactionType;
    private double amount;
    private double balance;
    private LocalDateTime date;
    private int accountNumber;

    @CommandHandler
    private TransactionAggregate(TransactionCreateCommand transactionCommand) {
        log.info("Transaction create aggregate :- " + transactionCommand);
        TransactionCreateEvent transactionCreateEvent = new TransactionCreateEvent();
        BeanUtils.copyProperties(transactionCommand, transactionCreateEvent);
        log.info("Create event created :- " + transactionCreateEvent);
        AggregateLifecycle.apply(transactionCreateEvent);
    }

    @EventSourcingHandler
    public void transactionCreateEventHandler(TransactionCreateEvent transactionCreateEvent) {
        log.info("Transaction create handler");
        this.id = transactionCreateEvent.getId();
        this.uid = transactionCreateEvent.getUid();
        this.transactionType =  transactionCreateEvent.getTransactionType();
        this.amount =  transactionCreateEvent.getAmount();
        this.balance =  transactionCreateEvent.getBalance();
        this.date =  transactionCreateEvent.getDate();
    }
}
