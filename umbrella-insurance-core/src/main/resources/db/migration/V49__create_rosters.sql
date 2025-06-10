CREATE TABLE IF NOT EXISTS public.rosters (
    roster_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    person_id int8 NULL,
    team_id int8 NULL,
    season_id int8 NULL,
    roster_name varchar NULL,
    team_member_type_id int8 NULL,
    CONSTRAINT team_players_pk PRIMARY KEY (roster_id)
);