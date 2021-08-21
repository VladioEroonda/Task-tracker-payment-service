package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.ClientResponseDto;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> getAllClients();

    ClientResponseDto addClient(ClientResponseDto requestDto);
}
