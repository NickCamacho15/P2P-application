package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao jdbcAccountDao;
    private BigDecimal ZERO = new BigDecimal("0");



    public JdbcTransactionDao(JdbcTemplate jdbcTemplate, JdbcAccountDao jdbcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAccountDao = jdbcAccountDao;
    }



    @Override
    public List<Transaction> listTransactions(long id) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, account_from_id, account_to_id, transaction_type, amount\n" +
                "FROM transactions\n" +
                "WHERE account_from_id = (SELECT account_id FROM account WHERE user_id = ?)\n" +
                "OR account_to_id = (SELECT account_id FROM account WHERE user_id = ?);";

        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, id, id);

        while(results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(long transactionId) {
        String sql = "SELECT transaction_id, account_from_id, account_to_id, transaction_type, amount\n" +
                "FROM transactions\n" +
                "WHERE transaction_id = ?;";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, transactionId);
        Transaction transaction = null;
        if (results.next()) {
            transaction = this.mapRowToTransaction(results);
        }
        return transaction;
    }

    @Override
    public boolean sendTransaction(long fromAccount, long toAccount, BigDecimal amount) {
        Account fromAc = new Account(fromAccount);
        Account toAc = new Account(toAccount);
        if (fromAccount == toAccount || amount.compareTo(fromAc.getBalance()) > 0 || amount.compareTo(ZERO) < 0 ){
            return false;
        }
        String sql = "INSERT INTO transactions (account_from_id, account_to_id, amount)\n" +
                "VALUES ((SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?)" +
                "RETURNING transaction_id;";

        Integer newTransactionId;
        try {
            newTransactionId = jdbcTemplate.queryForObject(sql, Integer.class, fromAccount, toAccount, amount);
        } catch (DataAccessException e){
            return false;
        }

        fromAc.equals(fromAc.getBalance().subtract(amount));
        toAc.equals(toAc.getBalance().add(amount));

        return true;
    }


    private Transaction mapRowToTransaction(SqlRowSet sqlRowSet){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(sqlRowSet.getLong("transaction_id"));
        transaction.setToAccount(sqlRowSet.getLong("account_to_id"));
        transaction.setFromAccount(sqlRowSet.getLong("account_from_id"));
        transaction.setTransactionType(sqlRowSet.getNString("transaction_type"));
        transaction.setAmount(sqlRowSet.getBigDecimal("amount"));
        return transaction;
    }
}
