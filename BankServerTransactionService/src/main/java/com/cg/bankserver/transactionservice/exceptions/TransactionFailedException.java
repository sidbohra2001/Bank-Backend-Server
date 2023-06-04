package com.cg.bankserver.transactionservice.exceptions;

public class TransactionFailedException extends Exception {
	public TransactionFailedException(String message) {
		super(message);
	}
}
