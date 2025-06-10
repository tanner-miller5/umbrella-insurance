CREATE TABLE IF NOT EXISTS public.matched_policy_states (
    matched_policy_state_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    matched_policy_state_name varchar NOT NULL,
    CONSTRAINT matched_policy_states_pk PRIMARY KEY (matched_policy_state_id),
    CONSTRAINT matched_policy_states_unique UNIQUE (matched_policy_state_name)
);