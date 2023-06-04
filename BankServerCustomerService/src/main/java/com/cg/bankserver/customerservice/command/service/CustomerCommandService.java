package com.cg.bankserver.customerservice.command.service;

import com.cg.bankserver.customerservice.command.entities.Customer;
import com.cg.bankserver.customerservice.dto.CustomerDTO;
import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.exceptions.UserCreationException;

public interface CustomerCommandService {
    Customer addCustomerDetails(CustomerDTO customerData) throws UserCreationException;
    Customer updateCustomerDetails(CustomerDTO customerData) throws CustomerDetailsNotFoundException;
    void deleteCustomerDetails(int id) throws CustomerDetailsNotFoundException;
}
