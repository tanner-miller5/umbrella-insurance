CREATE TABLE IF NOT EXISTS public.games (
    game_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    location_id int8 NULL,
    date_time timestamp NOT NULL,
    game_status_id int8 NULL,
    game_type_id int8 NULL,
    season_type_id int8 NULL,
    game_name varchar NOT NULL,
    CONSTRAINT games_pk PRIMARY KEY (game_id),
    CONSTRAINT games_unique UNIQUE (game_name)
);