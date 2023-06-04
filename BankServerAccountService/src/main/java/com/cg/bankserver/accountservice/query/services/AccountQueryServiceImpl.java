package com.cg.bankserver.accountservice.query.services;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.query.GetAccountByAccountNumberQuery;
import com.cg.bankserver.accountservice.query.entities.Account;

@Component
public class AccountQueryServiceImpl implements AccountQueryService {

    @Autowired private QueryGateway queryGateway;
    @Override
    public Account getAccountDetails(int accountNo) throws AccountDetailsNotFoundException {
        return queryGateway.query(new GetAccountByAccountNumberQuery(accountNo), ResponseTypes.optionalInstanceOf(Account.class)).join()
        		.orElseThrow(() -> new AccountDetailsNotFoundException("Account with account number :- " + accountNo + " could not be found"));
    }

    @Override
    public boolean validateAccountNumber(int accountNumber) {
        return false;
    }

    @Override
    public double checkBalance(int accountNumber) throws AccountDetailsNotFoundException {
        return 0;
    }

    @Override
    public Account getAccountByAccountNumber(int accountNumber) throws AccountDetailsNotFoundException {
        return queryGateway.query(new GetAccountByAccountNumberQuery(accountNumber),ResponseTypes.optionalInstanceOf(Account.class)).join()
                .orElseThrow(() -> new AccountDetailsNotFoundException("No account found with account number " + accountNumber));
    }
}
