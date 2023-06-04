package com.cg.bankserver.transactionservice.exceptions;

public class InvalidAccountNumberException extends Exception {
	public InvalidAccountNumberException(String message) {
		super(message);
	}
}
