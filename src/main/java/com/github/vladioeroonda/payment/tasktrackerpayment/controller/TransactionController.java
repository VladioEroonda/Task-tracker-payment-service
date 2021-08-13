package com.github.vladioeroonda.payment.tasktrackerpayment.controller;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.request.TransactionRequestDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Транзакция", description = "Отвечает за осуществление платёжных операций")
@RestController
@RequestMapping("/api/payment/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Совершение новой Транзакции")
    @PostMapping
    public ResponseEntity<TransactionResponseDto> doNewTransaction(@RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto transaction = transactionService.doNewTransaction(requestDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
