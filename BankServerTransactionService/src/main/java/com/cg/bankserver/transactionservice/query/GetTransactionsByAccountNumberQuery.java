package com.cg.bankserver.transactionservice.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetTransactionsByAccountNumberQuery {
    private int accountNumber;

    public GetTransactionsByAccountNumberQuery(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
