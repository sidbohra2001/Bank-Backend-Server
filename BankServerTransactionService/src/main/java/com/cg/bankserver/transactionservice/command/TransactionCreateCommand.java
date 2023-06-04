package com.cg.bankserver.transactionservice.command;

import com.cg.bankserver.transactionservice.entities.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateCommand {
    @TargetAggregateIdentifier
    private String uid;
    private int id;
    private TransactionType transactionType;
    private double amount;
    private double balance;
    private LocalDateTime date;
    private int accountNumber;
}
