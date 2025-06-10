CREATE TABLE IF NOT EXISTS public.passwords (
    password_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    salt varchar NULL,
    hashed_password varchar NULL,
    user_id int8 NULL,
    created_date_time timestamp NULL,
    CONSTRAINT passwords_pk PRIMARY KEY (password_id)
);