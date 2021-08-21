package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.request.ClientRequestDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.dto.request.TransactionRequestDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.TransactionType;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.TransactionBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {
    private final TransactionType TRANSACTION_TYPE = TransactionType.DEPOSIT;
    private final ClientRequestDto CLIENT_FROM
            = new ClientRequestDto(1L, "From", "abcd", new BigDecimal("100.00"));
    private final ClientRequestDto CLIENT_TO
            = new ClientRequestDto(2L, "To", "abcde", new BigDecimal("50.00"));
    private final BigDecimal AMOUNT = new BigDecimal("111.11");
    private final String COMMENT = "test_comment";

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ClientRepository clientRepository;

    @Test
    void doNewTransaction_ShouldThrowException_IfClientFrom_NotExistsById() {
        TransactionRequestDto expected =
                new TransactionRequestDto(TRANSACTION_TYPE, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);

        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenThrow(ClientNotFoundException.class);

        assertThrows(ClientNotFoundException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expected);
        });
    }

    @Test
    void doNewTransaction_ShouldThrowException_IfTransactionAmount_EqualsOrLessThanZero() {
        TransactionRequestDto expectedEqualsZero =
                new TransactionRequestDto(TRANSACTION_TYPE, CLIENT_FROM, CLIENT_TO, new BigDecimal("0.00"), COMMENT);
        TransactionRequestDto expectedLessThanZero =
                new TransactionRequestDto(TRANSACTION_TYPE, CLIENT_FROM, CLIENT_TO, new BigDecimal("-5.00"), COMMENT);

        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(new Client()));

        assertThrows(TransactionBadDataException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedEqualsZero);
        });
        assertThrows(TransactionBadDataException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedLessThanZero);
        });
    }

    @Test
    void doNewTransaction_ShouldReturnCorrectClientBalance_WhenDeposit() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TRANSACTION_TYPE, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal balanceBeforeDeposit = new BigDecimal("111.00");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", balanceBeforeDeposit);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));

        TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
        transactionService.doNewTransaction(expectedRequestDto);

        BigDecimal actualAmount = balanceBeforeDeposit.add(AMOUNT);

        assertEquals(expectedClientFrom.getBalance(), actualAmount);
    }

    @Test
    void doNewTransaction_ShouldReturnCorrectClientBalance_WhenWithdraw() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.WITHDRAW, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal balanceBeforeWithdraw = new BigDecimal("1111.11");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", balanceBeforeWithdraw);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));

        TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
        transactionService.doNewTransaction(expectedRequestDto);
        BigDecimal actualAmount = balanceBeforeWithdraw.subtract(AMOUNT);

        assertEquals(expectedClientFrom.getBalance(), actualAmount);
    }

    @Test
    void doNewTransaction_ShouldThrowException_IfClientBalance_LessThanWithdrawAmount() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.WITHDRAW, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal balanceBeforeWithdraw = new BigDecimal("10.00");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", balanceBeforeWithdraw);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));

        assertThrows(TransactionBadDataException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedRequestDto);
        });
    }

    @Test
    void doNewTransaction_ShouldThrowException_IfClientToNotExistsById() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.TRANSFER, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal balanceBeforeWithdraw = new BigDecimal("1111.11");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", balanceBeforeWithdraw);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));
        Mockito.when(clientRepository.findById(CLIENT_TO.getId()))
                .thenThrow(ClientNotFoundException.class);

        assertThrows(ClientNotFoundException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedRequestDto);
        });
    }

    @Test
    void doNewTransaction_ShouldThrowException_IfClientToEqualsClientFrom() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.TRANSFER, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal balanceBeforeWithdraw = new BigDecimal("1111.11");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", balanceBeforeWithdraw);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));
        Mockito.when(clientRepository.findById(CLIENT_TO.getId()))
                .thenReturn(Optional.of(expectedClientFrom));

        assertThrows(TransactionBadDataException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedRequestDto);
        });
    }

    @Test
    void doNewTransaction_ShouldThrowException_IfClientFromBalance_LessThanTransferAmount() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.TRANSFER, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", new BigDecimal("11.11"));
        Client expectedClientTo =
                new Client(2L, "To", "abcde", new BigDecimal("100.00"));
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));
        Mockito.when(clientRepository.findById(CLIENT_TO.getId()))
                .thenReturn(Optional.of(expectedClientTo));

        assertThrows(TransactionBadDataException.class, () -> {
            TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
            transactionService.doNewTransaction(expectedRequestDto);
        });
    }

    @Test
    void doNewTransaction_ShouldReturnCorrectClientBalance_WhenTransfer() {
        TransactionRequestDto expectedRequestDto =
                new TransactionRequestDto(TransactionType.TRANSFER, CLIENT_FROM, CLIENT_TO, AMOUNT, COMMENT);
        BigDecimal expectedClientFromBalance = new BigDecimal("1111.11");
        BigDecimal expectedClientToBalance = new BigDecimal("100.00");
        Client expectedClientFrom =
                new Client(1L, "From", "abcd", expectedClientFromBalance);
        Client expectedClientTo =
                new Client(2L, "To", "abcde", expectedClientToBalance);
        Mockito.when(clientRepository.findById(CLIENT_FROM.getId()))
                .thenReturn(Optional.of(expectedClientFrom));
        Mockito.when(clientRepository.findById(CLIENT_TO.getId()))
                .thenReturn(Optional.of(expectedClientTo));

        TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionRepository, clientRepository, new ModelMapper());
        transactionService.doNewTransaction(expectedRequestDto);

        BigDecimal actualClientFromBalance = expectedClientFromBalance.subtract(AMOUNT);
        BigDecimal actualClientToBalance = expectedClientToBalance.add(AMOUNT);

        assertEquals(expectedClientFrom.getBalance(), actualClientFromBalance);
        assertEquals(expectedClientTo.getBalance(), actualClientToBalance);
    }
}