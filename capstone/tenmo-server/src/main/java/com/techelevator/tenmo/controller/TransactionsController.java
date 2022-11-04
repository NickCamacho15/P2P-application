package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
public class TransactionsController {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public TransactionsController(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

        @RequestMapping(path = "/users/{user_id}/transactions", method = RequestMethod.GET)
    public List<Transaction> getTransactionsById(@PathVariable long user_id){
        return this.transactionDao.listTransactions(user_id);
        }

        @RequestMapping(path = "/transactions/{transaction_id}", method = RequestMethod.GET)
    public Transaction getTransactionByTransactionId(@PathVariable long transaction_id){

        return this.transactionDao.getTransactionById(transaction_id);
        }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( path = "/transactions/send", method = RequestMethod.POST)
    public boolean send(@RequestBody @Valid Transaction transaction) {

        BigDecimal senderBalance = accountDao.getBalanceById(transaction.getFromAccount());

        if (senderBalance.compareTo(transaction.getAmount()) == -1 ){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Amount your sending can't be more than current account balance");
        } else if (transaction.getFromAccount() == transaction.getToAccount()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot send money to your own account");
        }

      return this.transactionDao.sendTransaction(transaction.getFromAccount(), transaction.getToAccount(), transaction.getAmount());

    }




}
