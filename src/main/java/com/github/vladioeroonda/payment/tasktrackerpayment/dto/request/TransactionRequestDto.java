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
}
