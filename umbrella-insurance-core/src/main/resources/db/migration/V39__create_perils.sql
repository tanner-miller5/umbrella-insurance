CREATE TABLE IF NOT EXISTS public.perils (
    peril_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    peril_name varchar NULL,
    description varchar NULL,
    scale_name varchar NULL,
    min_magnitude float8 NULL,
    max_magnitude float8 NULL,
    CONSTRAINT perils_pk PRIMARY KEY (peril_id)
);