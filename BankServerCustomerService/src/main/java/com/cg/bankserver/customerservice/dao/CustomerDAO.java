package com.cg.bankserver.customerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bankserver.customerservice.entities.Customer;

import java.util.Optional;

public interface CustomerDAO extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
}
