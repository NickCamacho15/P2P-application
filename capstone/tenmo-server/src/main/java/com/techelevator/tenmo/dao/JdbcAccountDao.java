package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalanceById(long accountId) {
        String sql = "SELECT balance\n" +
                "FROM account \n" +
                "WHERE account_id = ?;";

        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
        return balance;
    }

    @Override
    public boolean createAccount(long id) {
        String sql = "INSERT INTO account (user_id, balance)\n" +
                "VALUES (?,1000)\n" +
                "RETURNING account_id;";
        Integer newAccountId;
        try {
            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, id);
        } catch (DataAccessException e) {
            return false;

        }
        return true;
    }




    private Account mapRowToAccount(SqlRowSet sqlRowSet){
        Account account = new Account();
        account.setAccountId(sqlRowSet.getLong("account_id"));
        account.setId(sqlRowSet.getLong("user_id"));
        account.setBalance(sqlRowSet.getBigDecimal("balance"));
        return account;
    }
}
