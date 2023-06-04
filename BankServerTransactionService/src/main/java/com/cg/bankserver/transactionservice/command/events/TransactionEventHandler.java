package com.cg.bankserver.transactionservice.command.events;

import com.cg.bankserver.transactionservice.query.entities.Transaction;
import com.cg.bankserver.transactionservice.query.repository.TransactionQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ProcessingGroup("transaction")
public class TransactionEventHandler {
    @Autowired private TransactionQueryRepository repository;

    @EventHandler
    Transaction on(TransactionCreateEvent transactionCreateEvent) {
        Transaction transactionEntity = new Transaction();
        log.info("transaction create event: " + transactionCreateEvent);
        BeanUtils.copyProperties(transactionCreateEvent, transactionEntity);
        log.info("transaction entity: " + transactionEntity);
        return repository.save(transactionEntity);
    }
}
