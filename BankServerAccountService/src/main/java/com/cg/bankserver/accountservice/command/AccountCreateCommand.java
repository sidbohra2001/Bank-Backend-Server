package com.cg.bankserver.accountservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class AccountCreateCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int accountNumber;
    private double balance;
    private int customerId;

}
