package com.github.vladioeroonda.payment.tasktrackerpayment.repository;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByAccountId(String accountId);
}
