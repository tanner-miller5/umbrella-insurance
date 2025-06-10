CREATE TABLE IF NOT EXISTS public.event_types (
    event_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    event_type_name varchar NOT NULL,
    CONSTRAINT event_types_pk PRIMARY KEY (event_type_id),
    CONSTRAINT event_types_unique UNIQUE (event_type_name)
);