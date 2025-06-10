CREATE TABLE IF NOT EXISTS public.encryption_keys (
    encryption_key_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    encryption_key_private_key varchar NOT NULL,
    encryption_key_public_key varchar NOT NULL,
    created_date_time timestamp NOT NULL,
    user_id int8 NULL,
    CONSTRAINT encryption_keys_pk PRIMARY KEY (encryption_key_id)
);