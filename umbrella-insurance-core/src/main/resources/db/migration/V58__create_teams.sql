CREATE TABLE IF NOT EXISTS public.teams (
    team_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    team_name varchar NOT NULL,
    team_type_id int8 NULL,
    location_id int8 NULL,
    first_season_id int8 NULL,
    last_season_id int8 NULL,
    logo_name varchar NULL,
    logo_image bytea NULL,
    level_of_competition_id int8 NULL,
    game_type_id int8 NULL,
    CONSTRAINT teams_pk PRIMARY KEY (team_id),
    CONSTRAINT teams_un UNIQUE (team_name, level_of_competition_id, game_type_id)
);