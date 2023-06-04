package com.cg.bankserver.accountservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.bankserver.accountservice.exceptions.*;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(AccountDetailsNotFoundException.class)
	public ResponseEntity<String> handleInvalidAccountNumberException(AccountDetailsNotFoundException e) {
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

	@ExceptionHandler ResponseEntity<String> handle(BankingServicesException e){
		return new ResponseEntity<String> (e.getMessage() , HttpStatus.UNPROCESSABLE_ENTITY); //
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<String> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
}
