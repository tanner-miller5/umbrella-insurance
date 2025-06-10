CREATE TABLE IF NOT EXISTS public.ads (
    ad_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    ad_name varchar NOT NULL,
    ad_data varchar NOT NULL,
    created_date_time timestamp NOT NULL,
    user_id int8 NULL,
    CONSTRAINT ads_pk PRIMARY KEY (ad_id),
    CONSTRAINT ads_unique UNIQUE (ad_name)
);