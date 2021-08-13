package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.TransactionBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.service.PaymentCheckService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentCheckImpl implements PaymentCheckService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public PaymentCheckImpl(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean checkPayment(
            String customerAccountId,
            String developerAccountId,
            BigDecimal amount,
            String comment
    ) {

        Client customer = clientRepository.findByAccountId(customerAccountId).orElseThrow(() -> {
            throw new ClientNotFoundException(
                    String.format("Клиент сервиса (отправитель) с р/с %s не найден",
                            customerAccountId)
            );
        });

        Client developer = clientRepository.findByAccountId(customerAccountId).orElseThrow(() -> {
            throw new ClientNotFoundException(
                    String.format("Клиент сервиса (получатель) с р/с %s не найден",
                            developerAccountId)
            );
        });

         Transaction payment =
                 transactionRepository.checkPayment(customerAccountId, developerAccountId, amount, comment);

         if (payment==null){
             throw new TransactionBadDataException("Некорректные сумма или комментарий к платежу. Обратитесь в поддержку");
         }

        return true;
    }
}
