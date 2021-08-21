package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;

import java.util.List;

public interface ClientHistoryService {
    List<TransactionResponseDto> getAllTransactionsByClientId(Long id);
}
