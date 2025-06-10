CREATE TABLE IF NOT EXISTS public.totps (
    totp_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    totp_code varchar NOT NULL,
    created_date_time timestamp NOT NULL,
    CONSTRAINT totps_pk PRIMARY KEY (totp_id)
);