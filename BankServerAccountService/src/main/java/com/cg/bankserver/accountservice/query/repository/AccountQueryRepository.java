package com.cg.bankserver.accountservice.query.repository;

import com.cg.bankserver.accountservice.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountQueryRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountNumberAndCustomerId(int accountNumber, int customerId);
}
