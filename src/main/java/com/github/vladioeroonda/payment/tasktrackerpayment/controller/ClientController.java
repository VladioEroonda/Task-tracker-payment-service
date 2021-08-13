package com.github.vladioeroonda.payment.tasktrackerpayment.controller;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.ClientResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Клиент", description = "Отвечает за операции платёжной системой с УЗ Клиента")
@RestController
@RequestMapping("/api/payment/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Получение списка Клиентов")
    @GetMapping()
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        List<ClientResponseDto> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(summary = "Добавление нового Клиента")
    @PostMapping
    public ResponseEntity<ClientResponseDto> addNewClient(@RequestBody ClientResponseDto requestDto) {
        ClientResponseDto client = clientService.addClient(requestDto);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}
