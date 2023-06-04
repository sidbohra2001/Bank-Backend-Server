package com.cg.bankserver.customerservice.command.aggregate;

import com.cg.bankserver.customerservice.command.*;
import com.cg.bankserver.customerservice.command.events.CustomerCreateEvent;
import com.cg.bankserver.customerservice.command.events.CustomerDeleteEvent;
import com.cg.bankserver.customerservice.command.events.CustomerUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class CustomerAggregate {
    @AggregateIdentifier
    private String uid;
    private int id;
    private String firstName;
    private String lastName;

    private String username;

    @CommandHandler
    public CustomerAggregate(CustomerCreateCommand customerCommand) {
        log.info("Create command :- " + customerCommand);
        CustomerCreateEvent customerCreateEvent = new CustomerCreateEvent();
        BeanUtils.copyProperties(customerCommand, customerCreateEvent);
        log.info("Create event created :- " + customerCreateEvent);
        AggregateLifecycle.apply(customerCreateEvent);
    }

    @CommandHandler
    public CustomerAggregate(CustomerUpdateCommand customerCommand) {
        CustomerUpdateEvent customerUpdateEvent = new CustomerUpdateEvent();
        BeanUtils.copyProperties(customerCommand, customerUpdateEvent);
        AggregateLifecycle.apply(customerUpdateEvent);
    }

    @CommandHandler
    public CustomerAggregate(CustomerDeleteCommand customerCommand) {
        log.info("Deleting command" );
        CustomerDeleteEvent customerDeleteEvent = new CustomerDeleteEvent();
        BeanUtils.copyProperties(customerCommand, customerDeleteEvent);
        AggregateLifecycle.apply(customerDeleteEvent);
    }

    @CommandHandler
    public CustomerAggregate(CustomerCreateRollBackCommand customerCommand) {
        log.info("rolling back customer creation");
        CustomerDeleteEvent customerDeleteEvent = new CustomerDeleteEvent();
        BeanUtils.copyProperties(customerCommand, customerDeleteEvent);
        AggregateLifecycle.apply(customerDeleteEvent);
    }

    @EventSourcingHandler
    public void customerCreateEventSourcingHandler(CustomerCreateEvent customerCreateEvent) {
        log.info("Create Handler");
        this.id =customerCreateEvent.getId();
        this.uid =customerCreateEvent.getUid();
        this.firstName= customerCreateEvent.getFirstName();
        this.lastName = customerCreateEvent.getLastName();
        this.username = customerCreateEvent.getUsername();
        log.info("Customer id : - " + id);
    }

    @EventSourcingHandler
    public void customerUpdateEventSourcingHandler(CustomerUpdateEvent customerUpdateEvent) {
        this.id =customerUpdateEvent.getId();
        this.uid =customerUpdateEvent.getUid();
        this.firstName= customerUpdateEvent.getFirstName();
        this.lastName = customerUpdateEvent.getLastName();
        this.username = customerUpdateEvent.getUsername();
    }

    @EventSourcingHandler
    public void customerDeleteEventSourcingHandler(CustomerDeleteEvent customerDeleteEvent) {
        log.info("Delete Handler");
        this.id =customerDeleteEvent.getId();
        this.uid =customerDeleteEvent.getUid();
    }

}
