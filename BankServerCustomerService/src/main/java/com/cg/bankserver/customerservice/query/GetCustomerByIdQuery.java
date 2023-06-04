package com.cg.bankserver.customerservice.query;

import lombok.Data;

@Data
public class GetCustomerByIdQuery {
    private int id;

    public GetCustomerByIdQuery(int id) {
        super();
        this.id = id;
    }
}
