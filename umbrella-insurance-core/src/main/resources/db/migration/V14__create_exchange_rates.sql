CREATE TABLE IF NOT EXISTS public.exchange_rates (
    exchange_rate_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    unit_id_1 int8 NULL,
    unit_id_2 int8 NULL,
    unit_1_to_unit_2_ratio double precision NOT NULL,
    CONSTRAINT exchange_rates_pk PRIMARY KEY (exchange_rate_id),
    CONSTRAINT exchange_rates_unique UNIQUE (unit_id_1,unit_id_2)
);