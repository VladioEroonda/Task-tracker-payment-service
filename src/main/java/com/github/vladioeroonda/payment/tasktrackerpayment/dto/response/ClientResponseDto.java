package com.github.vladioeroonda.payment.tasktrackerpayment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Dto Клиента(Ответ)")
public class ClientResponseDto {
    @Schema(description = "ID Клиента")
    private Long id;
    @Schema(description = "ФИО Клиента")
    private String name;
    @Schema(description = "ID банковского счета Клиента")
    private String accountId;
    @Schema(description = "Баланс Клиента")
    private BigDecimal balance;
}
