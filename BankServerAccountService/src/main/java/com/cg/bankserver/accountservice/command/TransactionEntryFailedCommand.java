package com.cg.bankserver.accountservice.command;

import com.cg.bankserver.accountservice.dto.TransactionType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionEntryFailedCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int transactionId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private int accountNumber;

}
