package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransferRepository extends JpaRepository<Transfer, Long> {
}
