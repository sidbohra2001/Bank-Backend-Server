//package com.cg.bankserver.accountservice.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cg.bankserver.accountservice.entities.*;
//import com.cg.bankserver.accountservice.exceptions.*;
//import com.cg.bankserver.accountservice.services.AccountService;
//
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//
//
//@RestController
//public class AccountController {
//
//	@Autowired
//	private AccountService bankService;
//
//	@GetMapping("/")
//	ResponseEntity<String> rootPath() {
//		return new ResponseEntity<String>("Welcome To Bank Service", HttpStatus.OK);
//	}
//
//	@PostMapping(value = { "/createAccount" })
//	ResponseEntity<Account> createAccount(@RequestBody Account account) throws AccountCreationException {
//		return new ResponseEntity<Account>(
//				bankService.createNewAccount(account), HttpStatus.CREATED);
//	}
//
//	@GetMapping("/checkBalance/{accountNumber}")
//	ResponseEntity<Double> checkBalance(@PathVariable int accountNumber) throws AccountDetailsNotFoundException {
//		return new ResponseEntity<Double>(bankService.checkBalance(accountNumber), HttpStatus.OK);
//	}
//
//	@PostMapping(value = { "/deposit" }, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@CircuitBreaker(name="transactionAccountBreaker",fallbackMethod = "transasctionAccountBreakerDeposit")
//	ResponseEntity<Double> deposit(@RequestBody DepositData depositData)
//			throws AccountDetailsNotFoundException, NegativeAmountException {
//		return new ResponseEntity<Double>(bankService.deposit(depositData.accountNumber, depositData.amount),
//				HttpStatus.OK);
//	}
//
//	@PostMapping("/withdraw")
//	@CircuitBreaker(name="transactionAccountBreaker",fallbackMethod = "transasctionAccountBreakerDeposit")
//	ResponseEntity<Double> withdraw(@RequestBody DepositData withdrawData)
//			throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException {
//		return new ResponseEntity<Double>(bankService.withdraw(withdrawData.accountNumber, withdrawData.amount),
//				HttpStatus.OK);
//	}
//
//	@PostMapping("/fundTransfer")
//	@CircuitBreaker(name="transactionAccountBreaker",fallbackMethod = "transactionAccountBreakerFundTransfer")
//	ResponseEntity<Double> fundTransfer(@RequestBody FundTransferData fundTransferData)
//			throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException,
//			TransactionFailedException {
//		return new ResponseEntity<Double>(bankService.fundTransfer(fundTransferData.accountNumber,
//				fundTransferData.receiverAccountNumber, fundTransferData.amount), HttpStatus.OK);
//	}
//
//	public ResponseEntity<String> transasctionAccountBreakerDeposit(DepositData data, Exception ex) {
//		String message = "Currently Transaction services are down !! Please Try Again after some Time";
//		return new ResponseEntity<String>(message, HttpStatus.OK);
//	}
//
//	public ResponseEntity<String> transactionAccountBreakerFundTransfer(FundTransferData data, Exception ex) {
//		String message = "Currently Transaction services are down !! Please Try Again after some Time";
//		return new ResponseEntity<String>(message, HttpStatus.OK);
//	}
//}
