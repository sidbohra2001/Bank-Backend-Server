package com.cg.bankserver.transactionservice.query.controller;

import com.cg.bankserver.transactionservice.exceptions.BankingServicesException;
import com.cg.bankserver.transactionservice.exceptions.TransactionDetailsNotFoundException;
import com.cg.bankserver.transactionservice.query.entities.Transaction;
import com.cg.bankserver.transactionservice.query.services.TransactionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionQueryController {

    @Autowired private TransactionQueryService transactionQueryService;

    @GetMapping("/getTransactionById")
    public ResponseEntity<Transaction> getTransactions(@RequestParam int id) throws TransactionDetailsNotFoundException {
        return ResponseEntity.ok(transactionQueryService.getTransactionById(id));
    }

    @GetMapping("/getTransactionByAccountId")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@RequestParam int accountId) throws BankingServicesException {
        return ResponseEntity.ok(transactionQueryService.getTransactionByAccountNumber(accountId));
    }

}
