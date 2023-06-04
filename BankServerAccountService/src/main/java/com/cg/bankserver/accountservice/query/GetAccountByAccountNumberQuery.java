package com.cg.bankserver.accountservice.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAccountByAccountNumberQuery {
    private int accountNumber;

    public GetAccountByAccountNumberQuery(int accountNumber) {
        this.accountNumber = accountNumber;
    }

}
