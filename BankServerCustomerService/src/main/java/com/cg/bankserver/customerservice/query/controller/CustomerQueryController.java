package com.cg.bankserver.customerservice.query.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bankserver.customerservice.dto.CustomerDTO;
import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
import com.cg.bankserver.customerservice.query.entities.Customer;
import com.cg.bankserver.customerservice.query.services.CustomerQueryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CustomerQueryController {

    @Autowired
    private CustomerQueryService service;
    @GetMapping(value = "/getCustomer" )
    public ResponseEntity<CustomerDTO> getCustomerDetails(@RequestParam("userid") int id) throws CustomerDetailsNotFoundException {

        Customer customer = service.getCustomerDetails(id);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
    }
}
