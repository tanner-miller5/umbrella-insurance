CREATE TABLE IF NOT EXISTS public.payment_types (
    payment_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    payment_type_name varchar NULL,
    CONSTRAINT payment_types_pk PRIMARY KEY (payment_type_id),
    CONSTRAINT payment_types_unique UNIQUE (payment_type_name)
);