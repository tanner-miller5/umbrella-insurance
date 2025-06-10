CREATE TABLE IF NOT EXISTS public.player_records (
    player_record_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    record_id int8 NULL,
    player_id int8 NULL,
    season_id int8 NULL,
    CONSTRAINT player_records_pk PRIMARY KEY (player_record_id),
    CONSTRAINT player_records_unique UNIQUE (season_id, player_id)
);