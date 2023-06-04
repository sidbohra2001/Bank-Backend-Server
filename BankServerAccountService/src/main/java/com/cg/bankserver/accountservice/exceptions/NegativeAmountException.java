package com.cg.bankserver.accountservice.exceptions;

public class NegativeAmountException extends Exception {
	public NegativeAmountException(String message) {
		super(message);
	}
}
