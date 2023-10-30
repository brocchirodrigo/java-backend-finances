package com.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
