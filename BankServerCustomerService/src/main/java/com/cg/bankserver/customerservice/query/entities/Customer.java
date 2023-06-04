package com.cg.bankserver.customerservice.query.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="Customer_Query")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private int id;

    @Column(unique = true)
    private String uid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="user_name")
    private String username;
}

