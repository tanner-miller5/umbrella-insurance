CREATE TABLE IF NOT EXISTS public.tournaments (
    tournament_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    tournament_name varchar NOT NULL,
    tournament_type_id int8 NULL,
    CONSTRAINT tournaments_pk PRIMARY KEY (tournament_id),
    CONSTRAINT tournaments_unique UNIQUE (tournament_name)
);