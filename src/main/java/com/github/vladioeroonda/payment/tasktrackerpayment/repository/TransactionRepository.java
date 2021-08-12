package com.github.vladioeroonda.payment.tasktrackerpayment.repository;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
