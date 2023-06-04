package com.cg.bankserver.transactionservice.command.events;

import com.cg.bankserver.transactionservice.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateEvent {
    private String uid;
    private int id;
    private TransactionType transactionType;
    private double amount;
    private double balance;
    private LocalDateTime date;
    private int accountNumber;

}
