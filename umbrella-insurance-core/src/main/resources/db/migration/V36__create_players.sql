CREATE TABLE IF NOT EXISTS public.players (
    player_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    person_id int8 NULL,
    game_type_id int8 NULL,
    height varchar NULL,
    weight varchar NULL,
    college varchar NULL,
    draft_info varchar NULL,
    player_status varchar NULL,
    jersey_number varchar NULL,
    player_position varchar NULL,
    experience varchar NULL,
    birthplace varchar NULL,
    CONSTRAINT players_pk PRIMARY KEY (player_id)
);