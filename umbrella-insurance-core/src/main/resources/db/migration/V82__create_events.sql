CREATE TABLE IF NOT EXISTS public.events (
    event_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    session_id int8 NULL,
    event_type_id int8 NULL,
    created_date_time timestamp NOT NULL,
    CONSTRAINT events_pk PRIMARY KEY (event_id)
);