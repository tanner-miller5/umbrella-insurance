CREATE TABLE IF NOT EXISTS public.specialties (
    specialty_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    specialty_name varchar NOT NULL,
    CONSTRAINT specialties_pk PRIMARY KEY (specialty_id),
    CONSTRAINT specialties_unique UNIQUE (specialty_name)
);