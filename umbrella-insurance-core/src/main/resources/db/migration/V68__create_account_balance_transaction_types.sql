CREATE TABLE IF NOT EXISTS public.account_balance_transaction_types (
    account_balance_transaction_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    account_balance_transaction_type_name varchar NOT NULL,
    CONSTRAINT account_balance_transaction_types_pk PRIMARY KEY (account_balance_transaction_type_id),
    CONSTRAINT account_balance_transaction_types_unique UNIQUE (account_balance_transaction_type_name)
);