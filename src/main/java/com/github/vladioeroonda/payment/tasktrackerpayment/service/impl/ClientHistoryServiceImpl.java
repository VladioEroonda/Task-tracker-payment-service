package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.ClientHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientHistoryServiceImpl implements ClientHistoryService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientHistoryServiceImpl(
            TransactionRepository transactionRepository,
            ClientRepository clientRepository,
            ModelMapper modelMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<TransactionResponseDto> getAllTransactionsByClientId(Long id) {

        Client clientFromRequest = clientRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ClientNotFoundException(
                            String.format("Клиент с id #%d не существует", id)
                    );
                });

        List<Transaction> transactions = transactionRepository.getAllTransactionsByClientId(id);
        return transactions.stream()
                .map(entity -> convertFromEntityToResponse(entity))
                .collect(Collectors.toList()
                );
    }

    private TransactionResponseDto convertFromEntityToResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }
}
