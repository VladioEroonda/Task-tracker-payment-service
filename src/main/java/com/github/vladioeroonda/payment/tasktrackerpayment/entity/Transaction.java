package com.github.vladioeroonda.payment.tasktrackerpayment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction", schema = "public")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "from_client_id", nullable = false)
    private Client fromClient;
    @ManyToOne
    @JoinColumn(name = "to_client_id", nullable = false)
    private Client toClient;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column
    private String comment;

    public Transaction() {
    }

    public Transaction(
            TransactionType type,
            Client fromClient,
            Client toClient,
            BigDecimal amount,
            LocalDateTime date,
            String comment
    ) {
        this.type = type;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.amount = amount;
        this.date = date;
        this.comment = comment;
    }

    public Transaction(
            Long id,
            TransactionType type,
            Client fromClient,
            Client toClient,
            BigDecimal amount,
            LocalDateTime date,
            String comment
    ) {
        this.id = id;
        this.type = type;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.amount = amount;
        this.date = date;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Client getFromClient() {
        return fromClient;
    }

    public void setFromClient(Client fromClient) {
        this.fromClient = fromClient;
    }

    public Client getToClient() {
        return toClient;
    }

    public void setToClient(Client toClient) {
        this.toClient = toClient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && type == that.type && Objects.equals(fromClient, that.fromClient) && Objects.equals(toClient, that.toClient) && Objects.equals(amount, that.amount) && Objects.equals(date, that.date) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, fromClient, toClient, amount, date, comment);
    }

    @Override
    public String toString() {
        return String.format("Transaction{ id=%d, type= %s, fromClient= %s, toClient= %s, amount= %f, date= %s, comment= %s }",
                id, type, fromClient, toClient, amount, date, comment);
    }
}
