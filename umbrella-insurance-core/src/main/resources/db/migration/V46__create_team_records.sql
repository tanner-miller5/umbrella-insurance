CREATE TABLE IF NOT EXISTS public.team_records (
    team_record_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    record_id int8 NULL,
    team_id int8 NULL,
    season_id int8 NULL,
    CONSTRAINT team_records_pk PRIMARY KEY (team_record_id),
    CONSTRAINT team_records_unique UNIQUE (season_id, team_id)
);