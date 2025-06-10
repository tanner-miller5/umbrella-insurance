CREATE TABLE IF NOT EXISTS public.weeks (
    week_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    week_number int8 NULL,
    week_title varchar NULL,
    week_start_date date NULL,
    week_end_date date NULL,
    CONSTRAINT weeks_pk PRIMARY KEY (week_id),
    CONSTRAINT weeks_unique UNIQUE (week_title)
);