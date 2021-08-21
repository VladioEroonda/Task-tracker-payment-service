package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.TransactionBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PaymentCheckServiceImplTest {
    private final String CUSTOMER_ACCOUNT_ID = "customer_test_id";
    private final String DEVELOPER_ACCOUNT_ID = "dev_test_id";
    private final BigDecimal AMOUNT = new BigDecimal("100.00");
    private final String COMMENT = "comment";

    @InjectMocks
    private PaymentCheckServiceImpl paymentCheckService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ClientRepository clientRepository;

    @Test
    void checkPayment_ShouldReturnTrue() {
        Mockito.when(clientRepository.findByAccountId(Mockito.anyString()))
                .thenReturn(Optional.of(new Client()));
        Mockito.when(transactionRepository.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT.toUpperCase(Locale.ROOT)))
                .thenReturn(new Transaction());

        boolean actual = paymentCheckService.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT);

        assertTrue(actual);
    }

    @Test
    void checkPayment_ShouldThrowException_IfCustomerNotExists() {

        Mockito
                .when(clientRepository.findByAccountId(CUSTOMER_ACCOUNT_ID))
                .thenThrow(ClientNotFoundException.class);

        assertThrows(ClientNotFoundException.class, () -> {
            paymentCheckService.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT);
        });
    }

    @Test
    void checkPayment_ShouldThrowException_IfDeveloperNotExists() {
        Mockito.when(clientRepository.findByAccountId(Mockito.anyString()))
                .thenReturn(Optional.of(new Client()));
        Mockito.when(clientRepository.findByAccountId(DEVELOPER_ACCOUNT_ID))
                .thenThrow(ClientNotFoundException.class);

        assertThrows(ClientNotFoundException.class, () -> {
            paymentCheckService.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT);
        });
    }

    @Test
    void checkPayment_ShouldThrowException_IfTransactionNotExists() {
        Mockito.when(clientRepository.findByAccountId(Mockito.anyString()))
                .thenReturn(Optional.of(new Client()));
        Mockito.when(transactionRepository.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT.toUpperCase(Locale.ROOT)))
                .thenReturn(null);

        assertThrows(TransactionBadDataException.class, () -> {
            paymentCheckService.checkPayment(CUSTOMER_ACCOUNT_ID, DEVELOPER_ACCOUNT_ID, AMOUNT, COMMENT);
        });
    }
}