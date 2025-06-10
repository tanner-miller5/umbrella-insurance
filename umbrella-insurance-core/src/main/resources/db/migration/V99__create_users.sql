CREATE TABLE IF NOT EXISTS public.users (
    user_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    created_date_time timestamp NOT NULL,
    person_id int8 NULL,
    email_address varchar NOT NULL,
    phone_number varchar NOT NULL,
    username varchar NOT NULL,
    is_email_address_verified bool NULL,
    is_phone_number_verified bool NULL,
    verification_method_id int8 NULL,
    is_auth_app_verified bool NULL,
    CONSTRAINT user4_pk PRIMARY KEY (user_id),
    CONSTRAINT user_v4_unique UNIQUE (person_id),
    CONSTRAINT profiles_email_address_unique UNIQUE (email_address),
    CONSTRAINT profiles_phone_number_unique UNIQUE (phone_number),
    CONSTRAINT profiles_username_unique UNIQUE (username)
);