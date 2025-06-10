CREATE TABLE IF NOT EXISTS public.team_types (
    team_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    team_type_name varchar NOT NULL,
    CONSTRAINT team_types_pk PRIMARY KEY (team_type_id),
    CONSTRAINT team_types_unique UNIQUE (team_type_name)
);