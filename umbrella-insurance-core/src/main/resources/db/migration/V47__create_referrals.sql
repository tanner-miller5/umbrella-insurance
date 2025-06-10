CREATE TABLE IF NOT EXISTS public.referrals (
    referral_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    referral_name varchar NOT NULL,
    CONSTRAINT referrals_pk PRIMARY KEY (referral_id),
    CONSTRAINT referrals_unique UNIQUE (referral_name)
);