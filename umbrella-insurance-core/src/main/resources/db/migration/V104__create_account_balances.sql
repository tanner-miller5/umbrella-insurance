CREATE TABLE IF NOT EXISTS public.account_balances (
    account_balance_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    account_balance_value double precision NOT NULL,
    account_balance_type_id int8 NULL,
    updated_date_time timestamp NOT NULL,
    user_id int8 NULL,
    unit_id int8 NULL,
    CONSTRAINT account_balances_pk PRIMARY KEY (account_balance_id),
    CONSTRAINT account_balances_unique UNIQUE (user_id, account_balance_type_id, unit_id)
);