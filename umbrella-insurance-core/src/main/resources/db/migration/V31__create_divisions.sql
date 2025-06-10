CREATE TABLE IF NOT EXISTS public.divisions (
    division_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    conference_id int8 NULL,
    division_name varchar NOT NULL,
    CONSTRAINT divisions_pk PRIMARY KEY (division_id),
    CONSTRAINT divisions_unique UNIQUE (division_name)
);