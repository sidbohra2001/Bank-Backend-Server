package com.cg.bankserver.accountservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateEvent {
    private String uid;
    private int accountNumber;
    private double balance;
    private int customerId;
}
