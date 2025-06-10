CREATE TABLE IF NOT EXISTS public.team_transaction_types (
    team_transaction_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    team_transaction_type_name varchar NOT NULL,
    CONSTRAINT team_transaction_types_pk PRIMARY KEY (team_transaction_type_id),
    CONSTRAINT team_transaction_types_unique UNIQUE (team_transaction_type_name)
);