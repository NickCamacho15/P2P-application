package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class TransactionsController {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public TransactionsController(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

        @RequestMapping(path = "users/{user_id}/transactions", method = RequestMethod.GET)
    public List<Transaction> getTransactionsById(@PathVariable long id){
        return this.transactionDao.listTransactions(id);
        }

        @RequestMapping(path = "/transactions/{transaction_id}", method = RequestMethod.GET)
    public Transaction getTransactionByTransactionId(@PathVariable long transactionId){
        return this.transactionDao.getTransactionById(transactionId);
        }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( path = "/transactions/send", method = RequestMethod.POST)
    public boolean send(@RequestBody @Valid Transaction transaction) {
        return this.transactionDao.sendTransaction(transaction.getFromAccount(), transaction.getToAccount(), transaction.getAmount());
    }




}
