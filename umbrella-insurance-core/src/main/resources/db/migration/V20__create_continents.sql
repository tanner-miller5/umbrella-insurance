CREATE TABLE IF NOT EXISTS public.continents (
    continent_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    continent_name varchar NULL,
    CONSTRAINT continents_pk PRIMARY KEY (continent_id),
    CONSTRAINT continents_un UNIQUE (continent_name)
);