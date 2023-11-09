package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepositRepository extends JpaRepository<Deposit, Long> {
}
