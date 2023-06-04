package com.cg.bankserver.transactionservice.entities;

public enum TransactionType {
	CREDIT(1),
	DEBIT(2);
	
	int ordinal;
	TransactionType(int ordinal) {
		this.ordinal = ordinal;
	}
	
	public int getOrdinal() {
		return ordinal;
	}
}
