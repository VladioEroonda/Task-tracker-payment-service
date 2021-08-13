package com.github.vladioeroonda.payment.tasktrackerpayment.repository;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "FROM Transaction t WHERE t.fromClient.id=:id OR t.toClient.id=:id")
    List<Transaction> getAllTransactionsByClientId(@Param("id") Long id);

    @Query(value = "FROM Transaction t " +
            "WHERE t.fromClient.accountId=:customer " +
            "AND t.toClient.accountId=:developer " +
            "AND t.amount=:amount " +
            "AND t.comment=:comment")
    Transaction checkPayment(@Param("customer") String customerAccountId,
                             @Param("developer") String developerAccountId,
                             @Param("amount") BigDecimal amount,
                             @Param("comment") String comment
    );

}
