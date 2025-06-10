CREATE TABLE IF NOT EXISTS public.team_member_types (
    team_member_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    team_member_type_name varchar NOT NULL,
    CONSTRAINT team_member_types_pk PRIMARY KEY (team_member_type_id),
    CONSTRAINT team_member_types_unique UNIQUE (team_member_type_name)
);