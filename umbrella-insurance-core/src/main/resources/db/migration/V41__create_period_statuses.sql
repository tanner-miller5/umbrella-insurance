CREATE TABLE IF NOT EXISTS public.period_statuses (
    period_status_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    period_status_name varchar NOT NULL,
    CONSTRAINT period_statuses_pk PRIMARY KEY (period_status_id),
    CONSTRAINT period_statuses_un UNIQUE (period_status_name)
);