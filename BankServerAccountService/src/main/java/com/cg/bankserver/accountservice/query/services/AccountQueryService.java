package com.cg.bankserver.accountservice.query.services;

import com.cg.bankserver.accountservice.query.entities.Account;
import com.cg.bankserver.accountservice.exceptions.*;

public interface AccountQueryService {
    public Account getAccountDetails(int accountNo)throws AccountDetailsNotFoundException;
    boolean validateAccountNumber(int accountNumber);
    double checkBalance(int accountNumber) throws AccountDetailsNotFoundException;
    Account getAccountByAccountNumber(int accountNumber) throws AccountDetailsNotFoundException;
}
