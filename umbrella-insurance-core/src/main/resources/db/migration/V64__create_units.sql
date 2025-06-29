CREATE TABLE IF NOT EXISTS public.units (
    unit_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    unit_name varchar NOT NULL,
    CONSTRAINT units_pk PRIMARY KEY (unit_id),
    CONSTRAINT units_un UNIQUE (unit_name)
);