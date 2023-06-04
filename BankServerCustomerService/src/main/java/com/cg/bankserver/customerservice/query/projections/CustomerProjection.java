package com.cg.bankserver.customerservice.query.projections;

import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.query.GetCustomerByIdQuery;
import com.cg.bankserver.customerservice.query.entities.Customer;
import com.cg.bankserver.customerservice.query.repositories.CustomerQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomerProjection {

    @Autowired
    CustomerQueryRepository customerDAO;



    @QueryHandler
    public Optional<Customer> handle(GetCustomerByIdQuery getCustomerByIdQuery) {
        log.info("finding customer : " + getCustomerByIdQuery.toString());
        return customerDAO.findById(getCustomerByIdQuery.getId());
    }
}
