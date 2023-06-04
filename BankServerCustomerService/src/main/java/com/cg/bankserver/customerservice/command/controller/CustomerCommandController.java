package com.cg.bankserver.customerservice.command.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bankserver.customerservice.command.service.CustomerCommandService;
import com.cg.bankserver.customerservice.dto.CustomerDTO;
import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.exceptions.UserCreationException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerCommandController {


    @Autowired
    private CustomerCommandService service;

    @PostMapping(value = "/addCustomer" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> addCustomerDetails(@Valid @RequestBody CustomerDTO receivedCustomerDTO) throws UserCreationException {
        log.info("Received Customer Details : " + receivedCustomerDTO);
        CustomerDTO responseCustomerDTO = new CustomerDTO();
        BeanUtils.copyProperties(service.addCustomerDetails(receivedCustomerDTO), responseCustomerDTO);
        return new ResponseEntity<CustomerDTO>(responseCustomerDTO, HttpStatus.CREATED) ;
    }

    @PatchMapping(value = "/updateCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> updateCustomerDetails(@RequestBody CustomerDTO customerData) throws CustomerDetailsNotFoundException {
        CustomerDTO responseCustomerData = new CustomerDTO();
        BeanUtils.copyProperties(service.updateCustomerDetails(customerData), responseCustomerData);
        return new ResponseEntity<CustomerDTO>(responseCustomerData, HttpStatus.OK);
    }

    @DeleteMapping (value = "/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable int customerId) throws CustomerDetailsNotFoundException {
        service.deleteCustomerDetails(customerId);
        return new ResponseEntity<String>("Customer Deleted", HttpStatus.NO_CONTENT);
    }
}
