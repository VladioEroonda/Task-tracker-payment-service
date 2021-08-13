package com.github.vladioeroonda.payment.tasktrackerpayment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dto Клиента(Запрос)")
public class ClientRequestDto {
    @Schema(description = "ID Клиента")
    private Long id;
    @Schema(description = "ФИО Клиента")
    private String name;
    @Schema(description = "ID банковского счета Клиента")
    private String accountId;
    @Schema(description = "Баланс Клиента")
    private BigDecimal balance;

    public ClientRequestDto() {
    }

    public ClientRequestDto(String name, String accountId, BigDecimal balance) {
        this.name = name;
        this.accountId = accountId;
        this.balance = balance;
    }

    public ClientRequestDto(Long id, String name, String accountId, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ClientRequestDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
