CREATE TABLE IF NOT EXISTS public.usernames_history (
    username_history_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    username varchar NOT NULL,
    created_date_time timestamp NULL,
    CONSTRAINT usernames_history_pk PRIMARY KEY (username_history_id)
);