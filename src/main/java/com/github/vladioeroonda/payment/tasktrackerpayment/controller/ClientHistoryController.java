package com.github.vladioeroonda.payment.tasktrackerpayment.controller;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.ClientHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "История операций клиента", description = "Список транзакций Клиента/история операций")
@RestController
@RequestMapping("/api/payment/history")
public class ClientHistoryController {

    private final ClientHistoryService clientHistoryService;

    public ClientHistoryController(ClientHistoryService clientHistoryService) {
        this.clientHistoryService = clientHistoryService;
    }

    @Operation(summary = "Получение списка транзакций (истории) Клиента по его ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<TransactionResponseDto>> getAllClientTransactions(@PathVariable Long id) {
        List<TransactionResponseDto> clientTransactions = clientHistoryService.getAllTransactionsByClientId(id);
        return new ResponseEntity<>(clientTransactions, HttpStatus.OK);
    }

}
