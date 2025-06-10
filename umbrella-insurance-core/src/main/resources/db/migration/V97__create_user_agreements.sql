CREATE TABLE IF NOT EXISTS public.user_agreements (
    user_agreement_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_agreement_name varchar NOT NULL,
    user_agreement_text varchar NOT NULL,
    did_agree bool NULL,
    updated_date_time timestamp NOT NULL,
    user_id int8 NULL,
    CONSTRAINT user_agreements_pk PRIMARY KEY (user_agreement_id),
    CONSTRAINT user_agreements_unique UNIQUE (user_id, user_agreement_name)
);