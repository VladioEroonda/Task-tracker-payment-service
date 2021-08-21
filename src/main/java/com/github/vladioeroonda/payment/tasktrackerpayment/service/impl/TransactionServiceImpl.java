package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.request.TransactionRequestDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.TransactionBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(
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
    public TransactionResponseDto doNewTransaction(TransactionRequestDto requestDto) {
        Client clientFrom = clientRepository
                .findById(requestDto.getFromClient().getId())
                .orElseThrow(() -> {
                    throw new ClientNotFoundException(
                            String.format("Клиент с id #%d не существует", requestDto.getFromClient().getId())
                    );
                });

        if (requestDto.getAmount().compareTo(new BigDecimal("0.00")) <= 0) {
            throw new TransactionBadDataException("Нулевое или отрицательное значение суммы операции");
        }

        Transaction transactionForSave = convertFromRequestToEntity(requestDto);
        transactionForSave.setDate(LocalDateTime.now());

        switch (transactionForSave.getType()) {
            case DEPOSIT:
                transactionForSave.setToClient(transactionForSave.getFromClient());// клиент From и To один и тот же
                clientFrom.setBalance(clientFrom.getBalance().add(transactionForSave.getAmount()));
                break;

            case WITHDRAW:
                transactionForSave.setToClient(transactionForSave.getFromClient());// клиент From и To один и тот же

                BigDecimal withdrawResult = accountRefill(clientFrom.getBalance(), transactionForSave.getAmount(), clientFrom);

                clientFrom.setBalance(withdrawResult);
                break;

            case TRANSFER:
                Client clientTo = clientRepository
                        .findById(requestDto.getToClient().getId())
                        .orElseThrow(() -> {
                            throw new ClientNotFoundException(
                                    String.format("Получатель платежа с id #%d не существует. Перевод невозможен",
                                            transactionForSave.getToClient().getId())
                            );
                        });

                if (Objects.equals(clientFrom.getId(), clientTo.getId())) {
                    throw new TransactionBadDataException("При TRANSFER получатель и отправитель платежа должны быть разными");
                }

                BigDecimal transferResult = accountRefill(clientFrom.getBalance(), transactionForSave.getAmount(), clientFrom);

                clientFrom.setBalance(transferResult);
                clientTo.setBalance(clientTo.getBalance().add(transactionForSave.getAmount()));

                break;
        }
        transactionRepository.save(transactionForSave);

        return convertFromEntityToResponse(transactionForSave);
    }

    private BigDecimal accountRefill(BigDecimal source, BigDecimal amount, Client clientFrom) {
        BigDecimal transferResult = source.subtract(amount);

        if (transferResult.compareTo(new BigDecimal("0.00")) < 0) {
            throw new TransactionBadDataException(
                    String.format("Не достаточно средств на счету для снятия. Ваш Баланс: %.2f",
                            clientFrom.getBalance().doubleValue())
            );
        }

        return transferResult;

    }

    private Transaction convertFromRequestToEntity(TransactionRequestDto requestDto) {
        return modelMapper.map(requestDto, Transaction.class);
    }

    private TransactionResponseDto convertFromEntityToResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }
}
