CREATE TABLE IF NOT EXISTS public.charities (
    charity_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    charity_name varchar NOT NULL,
    CONSTRAINT charities_pk PRIMARY KEY (charity_id),
    CONSTRAINT charities_unique UNIQUE (charity_name)
);