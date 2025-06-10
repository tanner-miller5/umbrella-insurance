CREATE TABLE IF NOT EXISTS public.locations (
    location_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    street_address_id int8 NULL,
    city_id int8 NULL,
    state_id int8 NULL,
    zip_code_id int8 NULL,
    country_id int8 NULL,
    location_name varchar NOT NULL,
    CONSTRAINT locations_pk PRIMARY KEY (location_id),
    CONSTRAINT locations_unique UNIQUE (location_name)
);