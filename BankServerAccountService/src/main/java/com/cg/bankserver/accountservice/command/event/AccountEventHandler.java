package com.cg.bankserver.accountservice.command.event;

import com.cg.bankserver.accountservice.command.repository.AccountCommandRepository;
import com.cg.bankserver.accountservice.dto.TransactionType;
import com.cg.bankserver.accountservice.query.entities.Account;
import com.cg.bankserver.accountservice.query.repository.AccountQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ProcessingGroup("account")
public class AccountEventHandler {
    @Autowired private AccountQueryRepository queryRepository;
    @Autowired private AccountCommandRepository commandRepository;
    @EventHandler
    Account on(AccountCreateEvent accountCreateEvent) {
        Account accountEntity = new Account();
        log.info("Account create event: " + accountCreateEvent);
        BeanUtils.copyProperties(accountCreateEvent, accountEntity);
        log.info("Account entity: " + accountEntity);
        return queryRepository.save(accountEntity);
    }

    @EventHandler
    Account on(AccountUpdateEvent accountUpdateEvent) {
        Account  accountEntity = new Account();
        log.info("Account update event: " + accountUpdateEvent);
        BeanUtils.copyProperties(accountUpdateEvent, accountEntity);
        log.info("Account  : " + accountEntity);
        return queryRepository.save(accountEntity);
    }

    @EventHandler
    void on(AccountUpdateRollBackedEvent accountUpdateRollBackedEvent) {
        log.error("====  "+accountUpdateRollBackedEvent);
        com.cg.bankserver.accountservice.command.entities.Account accountCommandEntity = commandRepository.findById(accountUpdateRollBackedEvent.getAccountNumber()).get();
        if(accountUpdateRollBackedEvent.getType() == TransactionType.DEBIT) {
            log.error("Before Roolbacked==== " +accountCommandEntity);
            accountCommandEntity.setBalance(accountCommandEntity.getBalance()+accountUpdateRollBackedEvent.getAmount());
            log.error("After Roolbacked==== " +accountCommandEntity);
        }
        else {
            log.error("====  Credit");
            accountCommandEntity.setBalance(accountCommandEntity.getBalance()-accountUpdateRollBackedEvent.getAmount());
        }
        commandRepository.save(accountCommandEntity);
        log.error("Final ==== " +accountCommandEntity);
    }
}
