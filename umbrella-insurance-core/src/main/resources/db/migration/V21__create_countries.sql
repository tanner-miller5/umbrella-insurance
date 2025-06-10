CREATE TABLE IF NOT EXISTS public.countries (
    country_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    country_name varchar NULL,
    CONSTRAINT countries_pk PRIMARY KEY (country_id),
    CONSTRAINT countries_un UNIQUE (country_name)
);