package com.cg.bankserver.accountservice.command;
import com.cg.bankserver.accountservice.dto.TransactionType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountUpdateRollBackedCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int transactionId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private int accountNumber;
}