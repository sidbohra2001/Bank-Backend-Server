package com.cg.bankserver.accountservice.command.aggregate;

import com.cg.bankserver.accountservice.command.AccountCreateCommand;
import com.cg.bankserver.accountservice.command.AccountUpdateCommand;
import com.cg.bankserver.accountservice.command.AccountUpdateRollBackedCommand;
import com.cg.bankserver.accountservice.command.event.AccountCreateEvent;
import com.cg.bankserver.accountservice.command.event.AccountUpdateEvent;
import com.cg.bankserver.accountservice.command.event.AccountUpdateRollBackedEvent;
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
public class AccountAggregate {
    @AggregateIdentifier
    private String uid;
    private int accountNumber;
    private double balance;
    private int customerId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;

    @CommandHandler
    private AccountAggregate(AccountCreateCommand accountCreateCommand) {
        log.info("Account create aggregate :- " + accountCreateCommand);
        AccountCreateEvent accountCreateEvent = new AccountCreateEvent();
        BeanUtils.copyProperties(accountCreateCommand, accountCreateEvent);
        log.info("Create event created :- " + accountCreateEvent);
        AggregateLifecycle.apply(accountCreateEvent);
    }

    @CommandHandler
    private AccountAggregate(AccountUpdateCommand accountUpdateCommand) {
        log.info("Account update aggregate :- " + accountUpdateCommand);
        AccountUpdateEvent accountUpdateEvent = new AccountUpdateEvent();
        BeanUtils.copyProperties(accountUpdateCommand, accountUpdateEvent);
        log.info("Update Event created :- " + accountUpdateEvent);
        AggregateLifecycle.apply(accountUpdateEvent);
    }

    @CommandHandler
    public AccountAggregate(AccountUpdateRollBackedCommand accountUpdateRollBackedCommand) {
        AccountUpdateRollBackedEvent accountUpdateRollBackedEvent = new AccountUpdateRollBackedEvent();
        BeanUtils.copyProperties(accountUpdateRollBackedCommand, accountUpdateRollBackedEvent);
        AggregateLifecycle.apply(accountUpdateRollBackedEvent);
    }

    @EventSourcingHandler
    public void accountCreateEventHandler(AccountCreateEvent accountCreateEvent) {
        log.info("Account create handler");
        this.accountNumber = accountCreateEvent.getAccountNumber();
        this.uid = accountCreateEvent.getUid();
        this.balance = accountCreateEvent.getBalance();
        this.customerId = accountCreateEvent.getCustomerId();
    }

    @EventSourcingHandler
    public void accountUpdateEventHandler(AccountUpdateEvent accountUpdateEvent) {
        log.info("Account update handler");
        this.uid =  accountUpdateEvent.getUid();
        this.accountNumber  =  accountUpdateEvent.getAccountNumber();
        this.balance =  accountUpdateEvent.getBalance();
        this.customerId =  accountUpdateEvent.getCustomerId();
        this.type = accountUpdateEvent.getType();
        this.amount = accountUpdateEvent.getAmount();
        this.date = accountUpdateEvent.getDate();
    }

    @EventSourcingHandler
    public void accountUpdateRollBackedEventHandler(AccountUpdateRollBackedEvent accountUpdateRollBackedEvent) {
        this.accountNumber=accountUpdateRollBackedEvent.getAccountNumber();
        this.uid =accountUpdateRollBackedEvent.getUid();
        this.balance= accountUpdateRollBackedEvent.getBalance();
        this.customerId = accountUpdateRollBackedEvent.getCustomerId();
        this.type=accountUpdateRollBackedEvent.getType();
        this.amount=accountUpdateRollBackedEvent.getAmount();
        this.date=accountUpdateRollBackedEvent.getDate();
    }
}
