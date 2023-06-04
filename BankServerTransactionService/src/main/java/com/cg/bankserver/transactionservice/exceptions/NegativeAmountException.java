package com.cg.bankserver.transactionservice.exceptions;

public class NegativeAmountException extends Exception {
	public NegativeAmountException(String message) {
		super(message);
	}
}
