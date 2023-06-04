package com.cg.bankserver.customerservice.query.services;

import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.query.entities.Customer;

public interface CustomerQueryService {
    Customer getCustomerDetails (int  id) throws CustomerDetailsNotFoundException;
}
