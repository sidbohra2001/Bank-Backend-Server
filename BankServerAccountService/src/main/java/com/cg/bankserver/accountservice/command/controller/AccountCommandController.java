package com.cg.bankserver.accountservice.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bankserver.accountservice.command.entities.Account;
import com.cg.bankserver.accountservice.command.services.AccountCommandService;
import com.cg.bankserver.accountservice.dto.DepositDTO;
import com.cg.bankserver.accountservice.dto.FundTransferDTO;
import com.cg.bankserver.accountservice.exceptions.AccountCreationException;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.exceptions.BankingServicesException;
import com.cg.bankserver.accountservice.exceptions.InsufficientBalanceException;
import com.cg.bankserver.accountservice.exceptions.NegativeAmountException;
import com.cg.bankserver.accountservice.exceptions.TransactionFailedException;

@RestController
public class AccountCommandController {
    @Autowired
    private AccountCommandService bankService;

    @GetMapping("/")
    ResponseEntity<String> rootPath() {
        return new ResponseEntity<String>("Welcome To Bank Service", HttpStatus.OK);
    }

    @PostMapping(value = { "/createAccount" })
    ResponseEntity<Account> createAccount(@RequestBody Account account) throws AccountCreationException, InsufficientBalanceException, BankingServicesException {
        return new ResponseEntity<Account>(
                bankService.createAccount(account), HttpStatus.CREATED);
    }

    @PostMapping(value = { "/deposit" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Double> deposit(@RequestBody DepositDTO depositData)
            throws AccountDetailsNotFoundException, NegativeAmountException, BankingServicesException {
        return new ResponseEntity<Double>(bankService.deposit(depositData.accountNumber, depositData.amount),
                HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    ResponseEntity<Double> withdraw(@RequestBody DepositDTO withdrawData)
            throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException, BankingServicesException {
        return new ResponseEntity<Double>(bankService.withdraw(withdrawData.accountNumber, withdrawData.amount),
                HttpStatus.OK);
    }

    @PostMapping("/fundTransfer")
    ResponseEntity<Double> fundTransfer(@RequestHeader("userid") int customerId, @RequestBody FundTransferDTO fundTransferData)
            throws AccountDetailsNotFoundException, InsufficientBalanceException, NegativeAmountException,
            TransactionFailedException, BankingServicesException {
        return new ResponseEntity<Double>(bankService.fundTransfer(fundTransferData.accountNumber,
                fundTransferData.receiverAccountNumber, fundTransferData.amount), HttpStatus.OK);
    }

    public ResponseEntity<String> transactionAccountBreakerDeposit(DepositDTO data, Exception ex) {
        String message = "Currently Transaction services are down !! Please Try Again after some Time";
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    public ResponseEntity<String> transactionAccountBreakerFundTransfer(FundTransferDTO data, Exception ex) {
        String message = "Currently Transaction services are down !! Please Try Again after some Time";
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
}
