package com.cg.bankserver.customerservice.query.repositories;

import com.cg.bankserver.customerservice.query.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerQueryRepository extends JpaRepository<Customer, Integer> {

}
