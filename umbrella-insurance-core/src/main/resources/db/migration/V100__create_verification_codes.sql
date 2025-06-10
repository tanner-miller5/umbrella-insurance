CREATE TABLE IF NOT EXISTS public.verification_codes (
    verification_code_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    verification_method_id int8 NULL,
    verification_code varchar NOT NULL,
    is_verified bool NOT NULL,
    expiration_date_time timestamp NOT NULL,
    verified_date_time timestamp NULL,
    minutes_to_verify int8 NOT NULL,
    max_attempts int8 NOT NULL,
    current_attempt int8 NOT NULL,
    CONSTRAINT verification_codes_pk PRIMARY KEY (verification_code_id)
);