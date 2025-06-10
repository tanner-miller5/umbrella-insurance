CREATE TABLE IF NOT EXISTS public.seasons (
    season_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    season_name varchar NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    CONSTRAINT seasons_pk PRIMARY KEY (season_id),
    CONSTRAINT seasons_unique UNIQUE (season_name)
);