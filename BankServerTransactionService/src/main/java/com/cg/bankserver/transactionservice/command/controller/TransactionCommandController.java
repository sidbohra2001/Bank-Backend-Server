package com.cg.bankserver.transactionservice.command.controller;

import com.cg.bankserver.transactionservice.command.service.TransactionCommandService;
import com.cg.bankserver.transactionservice.command.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionCommandController {

    @Autowired private TransactionCommandService transactionService;

    @PostMapping("/save")
    ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<Transaction>(transactionService.saveTransaction(transaction), HttpStatus.CREATED);
    }
}
