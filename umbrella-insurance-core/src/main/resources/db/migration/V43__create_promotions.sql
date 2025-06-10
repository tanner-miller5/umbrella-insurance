CREATE TABLE IF NOT EXISTS public.promotions (
    promotion_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    promotion_name varchar NOT NULL,
    CONSTRAINT promotions_pk PRIMARY KEY (promotion_id),
    CONSTRAINT promotions_unique UNIQUE (promotion_name)
);