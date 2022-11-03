package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    List<Transaction> listTransactions(long id);

    Transaction getTransactionById(long transactionId);


    boolean sendTransaction(long fromAccount, long toAccount, BigDecimal amount);
}
