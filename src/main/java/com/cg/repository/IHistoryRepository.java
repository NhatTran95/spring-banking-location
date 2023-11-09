package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoryRepository extends JpaRepository<History, Long> {
}
