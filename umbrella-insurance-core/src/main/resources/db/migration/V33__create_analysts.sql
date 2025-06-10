CREATE TABLE IF NOT EXISTS public.analysts (
    analyst_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    specialty_id int8 NULL,
    person_id int8 NULL,
    CONSTRAINT analysts_pk PRIMARY KEY (analyst_id),
    CONSTRAINT analysts_unique UNIQUE (person_id, specialty_id)
);