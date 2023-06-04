//package com.cg.bankserver.transactionservice.controller;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cg.bankserver.transactionservice.entities.*;
//import com.cg.bankserver.transactionservice.exceptions.*;
//import com.cg.bankserver.transactionservice.services.TransactionService;
//
//
//@RestController
//@RequestMapping("/transaction")
//public class TransactionController {
//
//	@Autowired
//	private TransactionService transactionService;
//
//	@GetMapping("/")
//	ResponseEntity<String> rootPath() {
//		return new ResponseEntity<String>("Welcome To Bank Service", HttpStatus.OK);
//	}
//
//	@GetMapping("/getAllTransactions/{accountNumber}")
//	ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable int accountNumber) {
//		return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactionDetails(accountNumber), HttpStatus.OK);
//	}
//
//	@PostMapping("/save")
//	ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
//		return new ResponseEntity<Transaction>(transactionService.saveTransaction(transaction), HttpStatus.CREATED);
//	}
//
//	@GetMapping("/showLastTenTransactions/{accountNumber}")
//	ResponseEntity<List<Transaction>> showLastTenTransactions(@PathVariable int accountNumber) throws InvalidAccountNumberException {
//		return new ResponseEntity<List<Transaction>>(transactionService.showLastTenTransactions(accountNumber), HttpStatus.OK);
//	}
//
//	@PostMapping("/showTransactionsBetweenDates/{accountNumber}")
//	ResponseEntity<List<Transaction>> showTransactionsBetweenDates(@PathVariable int accountNumber, @JsonArg("fromDate") String fromDate, @JsonArg("toDate") String toDate) throws InvalidAccountNumberException {
//		return new ResponseEntity<List<Transaction>>(transactionService.showTransactionsBetweenDates(accountNumber,
//								LocalDate.parse(fromDate).atStartOfDay(), LocalDate.parse(toDate).atStartOfDay()), HttpStatus.OK);
//	}
//}
