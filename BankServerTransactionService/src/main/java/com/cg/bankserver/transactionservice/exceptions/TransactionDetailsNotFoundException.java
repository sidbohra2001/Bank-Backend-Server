package com.cg.bankserver.transactionservice.exceptions;

public class TransactionDetailsNotFoundException extends Exception {
    public TransactionDetailsNotFoundException(String message) {
        super(message);
    }
}
