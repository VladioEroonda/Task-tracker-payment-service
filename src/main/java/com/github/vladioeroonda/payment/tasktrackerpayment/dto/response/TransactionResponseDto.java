package com.github.vladioeroonda.payment.tasktrackerpayment.dto.response;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.TransactionType;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.СurrencyType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dto Транзакции(Ответ)")
public class TransactionResponseDto {
    @Schema(description = "ID Транзакции")
    private Long id;
    @Schema(description = "Тип Транзакции")
    private TransactionType type;
    @Schema(description = "Клиент-инициатор Транзакции")
    private ClientResponseDto fromClient;
    @Schema(description = "Клиент-получатель Транзакции")
    private ClientResponseDto toClient;
    @Schema(description = "Сумма Транзакции")
    private BigDecimal amount;
}
