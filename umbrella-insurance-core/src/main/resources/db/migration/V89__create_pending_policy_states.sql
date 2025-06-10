CREATE TABLE IF NOT EXISTS public.pending_policy_states (
    pending_policy_state_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    pending_policy_state_name varchar NOT NULL,
    CONSTRAINT pending_policy_states_pk PRIMARY KEY (pending_policy_state_id),
    CONSTRAINT pending_policy_states_unique UNIQUE (pending_policy_state_name)
);