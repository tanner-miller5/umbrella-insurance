CREATE TABLE IF NOT EXISTS public.account_balance_transactions (
    account_balance_transaction_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    account_balance_transaction_name varchar NOT NULL,
    created_date_time timestamp NOT NULL,
    amount double precision NOT NULL,
    unit_id int8 NULL,
    account_balance_transaction_type_id int8 NULL,
    account_balance_transaction_status_id int8 NULL,
    CONSTRAINT account_balance_transactions_pk PRIMARY KEY (account_balance_transaction_id),
    CONSTRAINT account_balance_transactions_unique UNIQUE (account_balance_transaction_name)
);