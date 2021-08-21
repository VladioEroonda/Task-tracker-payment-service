package com.github.vladioeroonda.payment.tasktrackerpayment.service;

import java.math.BigDecimal;

public interface PaymentCheckService {
    boolean checkPayment(String customerAccountId, String developerAccountId, BigDecimal amount, String comment);
}
