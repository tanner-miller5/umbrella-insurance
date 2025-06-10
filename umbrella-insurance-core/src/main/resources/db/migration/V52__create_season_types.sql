CREATE TABLE IF NOT EXISTS public.season_types (
        season_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
        season_type_name varchar NOT NULL,
        CONSTRAINT season_types_pk PRIMARY KEY (season_type_id),
        CONSTRAINT season_types_unique UNIQUE (season_type_name)
);