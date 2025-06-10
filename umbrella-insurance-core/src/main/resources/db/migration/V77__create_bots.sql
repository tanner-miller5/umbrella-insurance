CREATE TABLE IF NOT EXISTS public.bots (
    bot_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    bot_name varchar NOT NULL,
    analyst_id int8 NULL,
    created_date_time timestamp NOT NULL,
    is_deleted bool NULL,
    is_disabled bool NULL,
    deleted_date_time timestamp NULL,
    amount_funded double precision NOT NULL,
    unit_id int8 NULL,
    CONSTRAINT bots_pk PRIMARY KEY (bot_id),
    CONSTRAINT bots_unique UNIQUE (bot_name)
);