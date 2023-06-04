package com.cg.bankserver.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bankserver.accountservice.entities.Account;

public interface AccountDAO extends JpaRepository<Account, Integer> {}
