package com.cg.bankserver.accountservice.command.aggregate;

import com.cg.bankserver.accountservice.command.TransactionEntryFailedCommand;
import com.cg.bankserver.accountservice.command.event.TransactionEntryFailedEvent;
import com.cg.bankserver.accountservice.dto.TransactionType;
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
    private int transactionId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private int accountNo;

    @CommandHandler
    public  TransactionAggregate(TransactionEntryFailedCommand transactionEntryFailedCommand) {
        TransactionEntryFailedEvent transactionEntryFailedEvent = new TransactionEntryFailedEvent();
        BeanUtils.copyProperties(transactionEntryFailedCommand	, transactionEntryFailedEvent);
        AggregateLifecycle.apply(transactionEntryFailedEvent);
    }
    @EventSourcingHandler
    public void accountUpdateEventSourcingHandler(TransactionEntryFailedEvent transactionEntryFailedEvent) {
        this.uid = transactionEntryFailedEvent.getUid();
        this.date = transactionEntryFailedEvent.getDate();
        this.amount=transactionEntryFailedEvent.getAmount();
        this.accountNo = transactionEntryFailedEvent.getAccountNumber();
        this.type=transactionEntryFailedEvent.getType();
    }
}
