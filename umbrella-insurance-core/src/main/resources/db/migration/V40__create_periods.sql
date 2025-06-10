CREATE TABLE IF NOT EXISTS public.periods (
    period_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    period_type_id int8 NULL,
    period_number int8 NOT NULL,
    period_status_id int8 NULL,
    game_id int8 NULL,
    CONSTRAINT periods_pk PRIMARY KEY (period_id)
);