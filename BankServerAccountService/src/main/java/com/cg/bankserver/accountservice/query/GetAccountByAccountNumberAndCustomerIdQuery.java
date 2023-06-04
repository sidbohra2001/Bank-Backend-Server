package com.cg.bankserver.accountservice.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAccountByAccountNumberAndCustomerIdQuery {
    private int accountNumber;
    private int customerId;

    public GetAccountByAccountNumberAndCustomerIdQuery(int accountNumber, int customerId) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
    }
}
