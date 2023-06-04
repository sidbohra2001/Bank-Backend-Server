package com.cg.bankserver.accountservice.query.projection;

import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.query.GetAccountByAccountNumberAndCustomerIdQuery;
import com.cg.bankserver.accountservice.query.GetAccountByAccountNumberQuery;
import com.cg.bankserver.accountservice.query.entities.Account;
import com.cg.bankserver.accountservice.query.repository.AccountQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class TransactionProjection {

    @Autowired private AccountQueryRepository repository;

    @QueryHandler
    public Optional<Account> handle(GetAccountByAccountNumberQuery getAccountByAccountNumberQuery) throws AccountDetailsNotFoundException {
        int accountNumber = getAccountByAccountNumberQuery.getAccountNumber();
        return repository.findById(accountNumber);
    }

    @QueryHandler
    public Optional<Account> handle(GetAccountByAccountNumberAndCustomerIdQuery  getAccountByAccountNumberAndIdQuery) {
        int  accountNumber = getAccountByAccountNumberAndIdQuery.getAccountNumber();
        int   customerId  = getAccountByAccountNumberAndIdQuery.getCustomerId();
        return repository.findByAccountNumberAndCustomerId(accountNumber,customerId);
    }
}
