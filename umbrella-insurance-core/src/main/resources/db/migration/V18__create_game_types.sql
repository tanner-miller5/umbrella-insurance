CREATE TABLE IF NOT EXISTS public.game_types (
    game_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    game_type_name varchar NOT NULL,
    CONSTRAINT game_types_pk PRIMARY KEY (game_type_id),
    CONSTRAINT game_types_game_name_unique UNIQUE (game_type_name)
);