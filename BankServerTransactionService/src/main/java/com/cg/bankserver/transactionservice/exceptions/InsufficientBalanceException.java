package com.cg.bankserver.transactionservice.exceptions;

public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
