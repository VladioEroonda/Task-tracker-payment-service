package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.ClientResponseDto;

import java.util.List;

public interface ClientService {

    /**
     * Получение всех Клиентов платёжного сервиса
     *
     * @return ClientResponseDto - список всех Клиентов
     */
    List<ClientResponseDto> getAllClients();

    /**
     * Добавление Клиента
     *
     * @param requestDto (ClientResponseDto), представляет обёртку(запрос) сущности Клиента
     * @return ClientResponseDto - добавленный клиент
     */
    ClientResponseDto addClient(ClientResponseDto requestDto);
}
