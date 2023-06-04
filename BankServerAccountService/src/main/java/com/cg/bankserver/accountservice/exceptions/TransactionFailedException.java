package com.cg.bankserver.accountservice.exceptions;

public class TransactionFailedException extends Exception {
	public TransactionFailedException(String message) {
		super(message);
	}
}
