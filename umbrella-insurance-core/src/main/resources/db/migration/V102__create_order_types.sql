CREATE TABLE IF NOT EXISTS public.order_types (
    order_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    order_type_name varchar NOT NULL,
    CONSTRAINT order_types_pk PRIMARY KEY (order_type_id),
    CONSTRAINT order_types_unique UNIQUE (order_type_name)
);