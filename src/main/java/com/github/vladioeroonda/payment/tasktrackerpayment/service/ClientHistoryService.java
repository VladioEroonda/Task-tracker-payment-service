package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;

import java.util.List;

public interface ClientHistoryService {
    List<TransactionResponseDto> getAllTransactionsByClientId(Long id);
}
