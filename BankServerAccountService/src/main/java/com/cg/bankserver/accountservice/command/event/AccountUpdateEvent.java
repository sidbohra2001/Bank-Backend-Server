package com.cg.bankserver.accountservice.command.event;

import com.cg.bankserver.accountservice.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountUpdateEvent {
    private String uid;
    private int accountNumber;
    private double balance;
    private int customerId;
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
}
