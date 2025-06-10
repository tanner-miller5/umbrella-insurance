CREATE TABLE IF NOT EXISTS public.email_addresses_history (
    email_address_history_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    email_address varchar NOT NULL,
    created_date_time timestamp NULL,
    CONSTRAINT email_addresses_history_pk PRIMARY KEY (email_address_history_id)
)