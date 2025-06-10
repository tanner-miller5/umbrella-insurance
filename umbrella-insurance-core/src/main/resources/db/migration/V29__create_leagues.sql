CREATE TABLE IF NOT EXISTS public.leagues (
    league_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    league_name varchar NOT NULL,
    game_type_id int8 NULL,
    CONSTRAINT leagues_pk PRIMARY KEY (league_id),
    CONSTRAINT leagues_unique UNIQUE (league_name, game_type_id)
);