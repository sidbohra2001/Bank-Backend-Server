package com.cg.bankserver.accountservice.command.event;

import com.cg.bankserver.accountservice.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntryFailedEvent {
    private String uid;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private int accountNumber;
}
