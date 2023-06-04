package com.cg.bankserver.transactionservice.query.entities;

import com.cg.bankserver.transactionservice.entities.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="transaction_query")
public class Transaction  {
    @Id
    private int id;

    @Column(unique=true)
    private String uid;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double amount;

    private double balance;

    private LocalDateTime date;

    @Column(name = "account_number")
    private int accountNumber;
}
