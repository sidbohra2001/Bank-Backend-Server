package com.cg.bankserver.accountservice.command.repository;

import com.cg.bankserver.accountservice.command.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountCommandRepository extends JpaRepository<Account, Integer> {
}
