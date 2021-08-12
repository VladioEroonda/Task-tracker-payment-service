package com.github.vladioeroonda.payment.tasktrackerpayment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dto Клиента(Запрос)")
public class ClientRequestDto {
    @Schema(description = "ID Клиента")
    private Long id;
    @Schema(description = "ФИО Клиента")
    private String name;
    @Schema(description = "ID банковского счета Клиента")
    private String accountId;
    @Schema(description = "Баланс Клиента")
    private BigDecimal balance;
}
