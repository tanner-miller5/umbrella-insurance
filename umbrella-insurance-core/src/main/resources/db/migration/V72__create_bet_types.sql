CREATE TABLE IF NOT EXISTS public.bet_types (
    bet_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    bet_type_name varchar NOT NULL,
    CONSTRAINT bet_types_pk PRIMARY KEY (bet_type_id),
    CONSTRAINT bet_types_unique UNIQUE (bet_type_name)
);