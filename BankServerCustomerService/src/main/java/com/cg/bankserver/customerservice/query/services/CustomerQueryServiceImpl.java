package com.cg.bankserver.customerservice.query.services;

import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.query.GetCustomerByIdQuery;
import com.cg.bankserver.customerservice.query.entities.Customer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerQueryServiceImpl implements CustomerQueryService{

    @Autowired
    private CommandGateway commandGateway;

    @Autowired private QueryGateway queryGateway;
    @Override
    public Customer getCustomerDetails(int id) throws CustomerDetailsNotFoundException {
    	System.err.println(id);
        return queryGateway.query(new GetCustomerByIdQuery(id), ResponseTypes.optionalInstanceOf(Customer.class)).join()
                 .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer with id " + id + " not found"));
    }



}
