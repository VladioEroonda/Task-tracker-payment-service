package com.github.vladioeroonda.payment.tasktrackerpayment.controller;

import com.github.vladioeroonda.payment.tasktrackerpayment.service.PaymentCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Клиент", description = "Внешний API")
@RestController
@RequestMapping("/api/payment/v1/check")
public class PaymentCheckController {

    private final PaymentCheckService paymentCheckService;

    public PaymentCheckController(PaymentCheckService paymentCheckService) {
        this.paymentCheckService = paymentCheckService;
    }

    @Operation(summary = "Проверка наличия платежа")
    @GetMapping
    public Boolean getPaymentCheckResult(
            @Parameter(description = "Счёт заказчика") @RequestParam String customerAccountId,
            @Parameter(description = "Счёт разработчика") @RequestParam String developerAccountId,
            @Parameter(description = "Сумма") @RequestParam BigDecimal amount,
            @Parameter(description = "Комментарий к платежу, должно быть указано название проекта") @RequestParam String comment
    ) {
        return paymentCheckService.checkPayment(customerAccountId,developerAccountId, amount, comment);
    }
}
