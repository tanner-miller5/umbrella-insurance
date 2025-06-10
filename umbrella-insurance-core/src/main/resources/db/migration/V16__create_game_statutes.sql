CREATE TABLE IF NOT EXISTS public.game_statuses (
    game_status_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    game_status_name varchar NOT NULL,
    CONSTRAINT game_statuses_pk PRIMARY KEY (game_status_id),
    CONSTRAINT game_statuses_unique UNIQUE (game_status_name)
);