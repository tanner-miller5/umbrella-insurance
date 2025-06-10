CREATE TABLE IF NOT EXISTS public.fees (
    fee_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    fee_percent double precision NULL,
    fixed_fee double precision NULL,
    unit_id int8 NOT NULL,
    fee_name varchar NOT NULL,
    CONSTRAINT fees_pk PRIMARY KEY (fee_id),
    CONSTRAINT fees_unique UNIQUE (fee_name)
);