CREATE TABLE IF NOT EXISTS public.holidays (
    holiday_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    holiday_name varchar NOT NULL,
    holiday_date date NOT NULL,
    CONSTRAINT holidays_pk PRIMARY KEY (holiday_id),
    CONSTRAINT holidays_unique UNIQUE (holiday_name)
);