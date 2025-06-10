CREATE TABLE IF NOT EXISTS public.bank_accounts (
    bank_account_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    account_number varchar NOT NULL,
    routing_number varchar NOT NULL,
    user_id int8 NULL,
    created_date_time timestamp NOT NULL,
    CONSTRAINT bank_accounts_pk PRIMARY KEY (bank_account_id)
);