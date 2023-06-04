package com.cg.bankserver.transactionservice.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetTransactionByIdQuery {

    private int id;

    public GetTransactionByIdQuery(int id) {
        this.id = id;
    }
}
