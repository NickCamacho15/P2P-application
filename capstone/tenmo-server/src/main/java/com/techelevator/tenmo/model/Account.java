package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private long accountId;
    private long id;
    private BigDecimal balance;


    public Account() {

    }

    public Account(long accountId, long id, BigDecimal balance) {
        this.accountId = accountId;
        this.id = id;
        this.balance = balance;
    }

    public Account(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
