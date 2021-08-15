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

                BigDecimal withdrawResult = clientFrom.getBalance().subtract(transactionForSave.getAmount());

                if (withdrawResult.compareTo(new BigDecimal("0.00")) < 0) {
                    throw new TransactionBadDataException(
                            String.format("Недостаточно средств на счету для снятия. Ваш Баланс: %.2f",
                                    clientFrom.getBalance().doubleValue())
                    );
                }
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

                if (clientFrom.getId() == clientTo.getId()) {
                    throw new TransactionBadDataException("При TRANSFER получатель и отправитель платежа должны быть разными");
                }

                BigDecimal transferResult =
                        clientFrom.getBalance().subtract(transactionForSave.getAmount());

                if (transferResult.compareTo(new BigDecimal("0.00")) < 0) {
                    throw new TransactionBadDataException(
                            String.format("Не достаточно средств на счету для снятия. Ваш Баланс: %.2f",
                                    clientFrom.getBalance().doubleValue())
                    );
                }
                clientFrom.setBalance(transferResult);
                clientTo.setBalance(clientTo.getBalance().add(transactionForSave.getAmount()));

//                clientRepository.save(clientTo);
                break;
        }
//        clientRepository.save(clientFrom);
        transactionRepository.save(transactionForSave);

        return convertFromEntityToResponse(transactionForSave);
    }

    private Transaction convertFromRequestToEntity(TransactionRequestDto requestDto) {
        return modelMapper.map(requestDto, Transaction.class);
    }

    private TransactionResponseDto convertFromEntityToResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }
}
