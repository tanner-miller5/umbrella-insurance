CREATE TABLE IF NOT EXISTS public.account_balance_transaction_statuses (
    account_balance_transaction_status_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    account_balance_transaction_status_name varchar NOT NULL,
    CONSTRAINT account_balance_transaction_statuses_pk PRIMARY KEY (account_balance_transaction_status_id),
    CONSTRAINT account_balance_transaction_statuses_unique UNIQUE (account_balance_transaction_status_name)
);