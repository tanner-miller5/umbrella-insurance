CREATE TABLE IF NOT EXISTS public.pending_bet_states (
    pending_bet_state_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    pending_bet_state_name varchar NOT NULL,
    CONSTRAINT pending_bet_states_pk PRIMARY KEY (pending_bet_state_id),
    CONSTRAINT pending_bet_states_unique UNIQUE (pending_bet_state_name)
);