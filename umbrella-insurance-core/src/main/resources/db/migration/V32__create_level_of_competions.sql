CREATE TABLE IF NOT EXISTS public.level_of_competitions (
    level_of_competition_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    level_of_competition_name varchar NOT NULL,
    CONSTRAINT level_of_competitions_pk PRIMARY KEY (level_of_competition_id)
);