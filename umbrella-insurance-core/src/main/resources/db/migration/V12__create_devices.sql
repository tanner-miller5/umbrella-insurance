CREATE TABLE IF NOT EXISTS public.devices (
    id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    ip_address varchar NOT NULL,
    user_agent varchar NOT NULL,
    device_name varchar NOT NULL,
    created_date_time timestamp NOT NULL,
    CONSTRAINT devices_pk PRIMARY KEY (id),
    CONSTRAINT devices_unique UNIQUE (device_name)
);