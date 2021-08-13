package com.github.vladioeroonda.payment.tasktrackerpayment.dto.request;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dto Транзакции(Запрос)")
public class TransactionRequestDto {
    @Schema(description = "ID Транзакции")
    private Long id;
    @Schema(description = "Тип Транзакции")
    private TransactionType type;
    @Schema(description = "Клиент-инициатор Транзакции")
    private ClientRequestDto fromClient;
    @Schema(description = "Клиент-получатель Транзакции")
    private ClientRequestDto toClient;
    @Schema(description = "Сумма Транзакции")
    private BigDecimal amount;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(
            TransactionType type,
            ClientRequestDto fromClient,
            ClientRequestDto toClient,
            BigDecimal amount
    ) {
        this.type = type;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.amount = amount;
    }

    public TransactionRequestDto(
            Long id,
            TransactionType type,
            ClientRequestDto fromClient,
            ClientRequestDto toClient,
            BigDecimal amount
    ) {
        this.id = id;
        this.type = type;
        this.fromClient = fromClient;
        this.toClient = toClient;
        this.amount = amount;
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

    public ClientRequestDto getFromClient() {
        return fromClient;
    }

    public void setFromClient(ClientRequestDto fromClient) {
        this.fromClient = fromClient;
    }

    public ClientRequestDto getToClient() {
        return toClient;
    }

    public void setToClient(ClientRequestDto toClient) {
        this.toClient = toClient;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionRequestDto{" +
                "id=" + id +
                ", type=" + type +
                ", fromClient=" + fromClient +
                ", toClient=" + toClient +
                ", amount=" + amount +
                '}';
    }
}
