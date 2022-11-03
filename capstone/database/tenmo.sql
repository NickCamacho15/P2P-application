BEGIN TRANSACTION;

DROP TABLE IF EXISTS transactions, account, tenmo_user;

DROP SEQUENCE IF EXISTS seq_transactions_id,seq_account_id, seq_user_id;
	
-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transactions_id
	INCREMENT BY 1
	START WITH 1001
	NO MAXVALUE;
	
CREATE TABLE transactions (
	transaction_id int NOT NULL DEFAULT nextval('seq_transactions_id'),
	account_from_id int NOT NULL,
	account_to_id int NOT NULL,
	transaction_type varchar(50) NOT NULL,
	amount decimal(13,2) NOT NULL,
	CONSTRAINT PK_transactions PRIMARY KEY (transaction_id),
	CONSTRAINT FK_transactions_from_acc FOREIGN KEY (account_from_id) REFERENCES account (account_id),
	CONSTRAINT FK_transactions_to_acc FOREIGN KEY (account_to_id) REFERENCES account (account_id)
);
-- ROLLBACK;
COMMIT TRANSACTION;
