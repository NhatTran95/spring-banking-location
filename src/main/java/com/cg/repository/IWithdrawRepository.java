package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {
}
