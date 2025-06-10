CREATE TABLE IF NOT EXISTS public.conferences (
    conference_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    league_id int8 NULL,
    conference_name varchar NULL,
    CONSTRAINT conferences_pk PRIMARY KEY (conference_id),
    CONSTRAINT conferences_unique UNIQUE (conference_name)
);