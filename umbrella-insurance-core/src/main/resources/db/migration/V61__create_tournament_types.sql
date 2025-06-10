CREATE TABLE IF NOT EXISTS public.tournament_types (
    tournament_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    tournament_type_name varchar NOT NULL,
    CONSTRAINT tournament_types_pk PRIMARY KEY (tournament_type_id),
    CONSTRAINT tournament_types_unique UNIQUE (tournament_type_name)
);