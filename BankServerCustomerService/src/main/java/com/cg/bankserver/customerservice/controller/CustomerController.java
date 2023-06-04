//package com.cg.bankserver.customerservice.controller;
//
//import com.cg.bankserver.customerservice.dto.CustomerDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cg.bankserver.customerservice.entities.Customer;
//import com.cg.bankserver.customerservice.exceptions.CustomerDetailsNotFoundException;
//import com.cg.bankserver.customerservice.services.CustomerService;
//
//@RestController
//public class CustomerController {
//
//	@Autowired
//	private CustomerService customerService;
//
//	@GetMapping("/")
//	ResponseEntity<String> rootPath() {
//		return new ResponseEntity<String>("Welcome To Bank Service", HttpStatus.OK);
//	}
//
//	@GetMapping("/getCustomer/{customerId}")
//	ResponseEntity<Customer> getCustomer(@PathVariable int customerId) throws CustomerDetailsNotFoundException {
//		return new ResponseEntity<Customer>(customerService.getCustomerDetail(customerId), HttpStatus.OK);
//	}
//
//	@PostMapping("/createCustomer")
//	ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerData) {
//		return new ResponseEntity<Customer>(customerService.addCustomerDetail(customerData), HttpStatus.CREATED);
//	}
//}
