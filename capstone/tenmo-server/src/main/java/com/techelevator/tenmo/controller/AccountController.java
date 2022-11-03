package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/accounts/{account_id}?balance=", method = RequestMethod.GET)
    public BigDecimal getBalanceByAccountId(long accountId){
        return this.accountDao.getBalanceById(accountId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/accounts", method = RequestMethod.POST)
    public boolean create(@RequestBody @Valid Account account){
        return this.accountDao.createAccount(account.getId());
    }

}
