package com.github.vladioeroonda.payment.tasktrackerpayment.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Проект", description = "Отвечает за операции с УЗ Клиента")
@RestController
@RequestMapping("/api/payment/operations")
public class ClientController {

}
