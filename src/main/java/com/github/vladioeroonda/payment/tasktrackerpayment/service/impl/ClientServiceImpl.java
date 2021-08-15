package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;


import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.ClientResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(entity -> convertFromEntityToResponse(entity))
                .collect(Collectors.toList()
                );
    }

    @Transactional
    @Override
    public ClientResponseDto addClient(ClientResponseDto requestDto) {

        if (requestDto.getName() == null || requestDto.getName().isBlank()) {
            throw new ClientBadDataException("Некорректные ФИО Клиента");
        }

        boolean isUuidBusy;
        String accountId;

        do {
            accountId = UUID.randomUUID().toString();
            isUuidBusy = clientRepository.findByAccountId(accountId).isPresent();
        } while (isUuidBusy);

        Client clientForSave = new Client(
                requestDto.getName(),
                accountId,
                new BigDecimal("0.00")
        );
        clientRepository.save(clientForSave);

        return convertFromEntityToResponse(clientForSave);
    }

    private ClientResponseDto convertFromEntityToResponse(Client client) {
        return modelMapper.map(client, ClientResponseDto.class);
    }
}
