package com.cg.bankserver.customerservice.command.repositories;

import com.cg.bankserver.customerservice.command.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCommandRepository extends JpaRepository<Customer, Integer> {

}
