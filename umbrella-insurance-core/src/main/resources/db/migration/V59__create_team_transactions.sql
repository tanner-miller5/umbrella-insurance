CREATE TABLE IF NOT EXISTS public.team_transactions (
    team_transaction_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    date_of_team_transaction date NULL,
    team_transaction_type_id int8 NULL,
    description varchar NOT NULL,
    team_id int8 NULL,
    CONSTRAINT team_transactions_pk PRIMARY KEY (team_transaction_id),
    CONSTRAINT team_transactions_unique UNIQUE (team_id, description)
);