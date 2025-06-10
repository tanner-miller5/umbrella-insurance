CREATE TABLE IF NOT EXISTS public.records (
    record_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    wins int8 NOT NULL,
    "ties" int8 NOT NULL,
    losses int8 NOT NULL,
    record_name varchar NOT NULL,
    CONSTRAINT records_pk PRIMARY KEY (record_id),
    CONSTRAINT records_unique UNIQUE (record_name)
);