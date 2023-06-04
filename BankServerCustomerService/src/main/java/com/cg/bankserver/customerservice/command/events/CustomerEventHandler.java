package com.cg.bankserver.customerservice.command.events;

import com.cg.bankserver.customerservice.query.entities.Customer;
import com.cg.bankserver.customerservice.query.repositories.CustomerQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ProcessingGroup("customer")
public class CustomerEventHandler {

    @Autowired
    private CustomerQueryRepository customerDAO;

    @EventHandler
    Customer on(CustomerCreateEvent customerCreateEvent) {
        Customer customerEntity =  new Customer();
        log.info("customer create event: " + customerCreateEvent);
        BeanUtils.copyProperties(customerCreateEvent, customerEntity);
        log.info("customer entity: " + customerEntity);
        return customerDAO.save(customerEntity);
    }
    @EventHandler
    Customer on(CustomerUpdateEvent customerUpdateEvent) {
        Customer customerEntity =  new Customer();
        log.info("Customer update event" + customerUpdateEvent);
        BeanUtils.copyProperties(customerUpdateEvent, customerEntity);
        return customerDAO.save(customerEntity);
    }

    @EventHandler
    void on(CustomerDeleteEvent customerDeleteEvent) {
        log.info("Customer delete event : " + customerDeleteEvent);
        customerDAO.deleteById(customerDeleteEvent.getId());
    }
}
