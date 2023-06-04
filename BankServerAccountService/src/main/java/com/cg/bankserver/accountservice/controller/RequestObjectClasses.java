package com.cg.bankserver.accountservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class DepositData {
	public int accountNumber;
	public double amount;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class FundTransferData extends DepositData {
	public int receiverAccountNumber;
}