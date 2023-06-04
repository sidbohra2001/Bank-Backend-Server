package com.cg.bankserver.accountservice.query.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account_query")
public class Account {
    @Id
    @Column(name = "account_number")
    private int accountNumber;

    @Column(unique = true)
    private String uid;

    private double balance;

    @Column(name="customer_id")
    private int customerId;
}

