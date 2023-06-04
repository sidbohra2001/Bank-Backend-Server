package com.cg.bankserver.accountservice.command;

import com.cg.bankserver.accountservice.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int accountNumber;
    private double balance;
    private int customerId;
    private LocalDateTime date;
    private TransactionType transactionType;
    private double amount;
}
