CREATE TABLE IF NOT EXISTS public.people (
    person_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    first_name varchar NOT NULL,
    middle_name varchar NULL,
    surname varchar NOT NULL,
    date_of_birth date NOT NULL,
    ssn varchar NULL,
    CONSTRAINT people_pk PRIMARY KEY (person_id),
    CONSTRAINT people_ssn_unique UNIQUE (ssn)
);