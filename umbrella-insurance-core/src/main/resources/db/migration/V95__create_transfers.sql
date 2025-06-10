CREATE TABLE IF NOT EXISTS public.transfers (
    transfer_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    unit_id int8 NULL,
    amount double precision NULL,
    user_id int8 NULL,
    is_voided bool NULL,
    voided_date_time timestamp NULL,
    posted_date_time timestamp NULL,
    created_date_time timestamp NOT NULL,
    voided_event_id int8 NULL,
    transfer_name varchar NOT NULL,
    CONSTRAINT transfers_pk PRIMARY KEY (transfer_id),
    CONSTRAINT transfers_unique UNIQUE (transfer_name)
);