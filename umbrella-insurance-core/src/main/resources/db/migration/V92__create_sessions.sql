CREATE TABLE IF NOT EXISTS public.sessions (
    session_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    session_code varchar NULL,
    start_date_time timestamp NOT NULL,
    end_date_time timestamp NOT NULL,
    minutes_to_expire int8 NOT NULL,
    device_id int8 NULL,
    verification_code_id int8 NULL,
    did_sign_out bool NOT NULL,
    CONSTRAINT sessions_pk PRIMARY KEY (session_id),
    CONSTRAINT sessions_unique UNIQUE (session_code)
);