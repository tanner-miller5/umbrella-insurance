CREATE TABLE IF NOT EXISTS public.period_types (
    period_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    period_type_name varchar NOT NULL,
    CONSTRAINT period_types_pk PRIMARY KEY (period_type_id),
    CONSTRAINT period_types_unique UNIQUE (period_type_name)
);