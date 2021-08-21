package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.request.TransactionRequestDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;

public interface TransactionService {
    TransactionResponseDto doNewTransaction(TransactionRequestDto requestDto);
}
