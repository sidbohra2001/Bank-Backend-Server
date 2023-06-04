package com.cg.bankserver.customerservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.cg.bankserver.customerservice.command.UserCreationFailedCommand;
import com.cg.bankserver.customerservice.command.events.UserCreationFailedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class UserAggregate {
    @AggregateIdentifier
    private String uid;
    private int id;
    private String username;
    private String password;
    @CommandHandler
    public  UserAggregate(UserCreationFailedCommand userCreationFailedCommand) {
        UserCreationFailedEvent userCreationFailedEvent = new UserCreationFailedEvent();
        BeanUtils.copyProperties(userCreationFailedCommand	, userCreationFailedEvent);
        AggregateLifecycle.apply(userCreationFailedEvent);
    }
    @EventSourcingHandler
    public void userCreationEventSourcingHandler(UserCreationFailedEvent userCreationFailedEvent) {
        this.uid = userCreationFailedEvent.getUid();
        this.id = userCreationFailedEvent.getId();
        this.username = userCreationFailedEvent.getUsername();
        this.password = userCreationFailedEvent.getPassword();
    }
}
