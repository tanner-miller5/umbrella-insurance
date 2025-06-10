CREATE TABLE IF NOT EXISTS public.matched_policies (
    matched_policy_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    created_date_time timestamp NULL,
    pending_insured_policy_id int8 NULL,
    pending_insurer_policy_id int8 NULL,
    premium double precision NULL,
    coverage_amount double precision NULL,
    matched_policy_state_id int8 NULL,
    policy_start_date date NULL,
    policy_end_date date NULL,
    insured_fee_id int8 NULL,
    insurer_fee_id int8 NULL,
    implied_probability double precision NULL,
    CONSTRAINT matched_policies_pk PRIMARY KEY (matched_policy_id)
);