CREATE TABLE IF NOT EXISTS public.street_addresses (
    street_address_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    street_address_line_1 varchar NOT NULL,
    street_address_line_2 varchar NULL,
    CONSTRAINT street_addresses_pk PRIMARY KEY (street_address_id)
);