package com.cg.bankserver.accountservice.query.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bankserver.accountservice.dto.AccountDTO;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.query.services.AccountQueryService;

@RestController
public class AccountQueryController {

    @Autowired private AccountQueryService accountQueryService;
    @GetMapping("/getAccountDetails")
    public ResponseEntity<AccountDTO> getAccountDetails(@RequestParam int accountNumber) throws AccountDetailsNotFoundException {
        AccountDTO responseAccount = new AccountDTO();
        BeanUtils.copyProperties(accountQueryService.getAccountByAccountNumber(accountNumber), responseAccount);
        return new ResponseEntity<AccountDTO>(responseAccount, HttpStatus.OK);
    }
}
