package com.cg.bankserver.transactionservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.bankserver.transactionservice.exceptions.*;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(InvalidAccountNumberException.class)
	public ResponseEntity<String> handleInvalidAccountNumberException(InvalidAccountNumberException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NegativeAmountException.class)
	public ResponseEntity<String> handleNegativeAmountException(NegativeAmountException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TransactionDetailsNotFoundException.class)
	public  ResponseEntity<String> handleTransactionsNotFoundException(TransactionDetailsNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BankingServicesException.class)
	public  ResponseEntity<String> handleBankServiceException(BankingServicesException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
}
