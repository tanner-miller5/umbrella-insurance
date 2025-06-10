CREATE TABLE IF NOT EXISTS public.matched_bets (
    matched_bet_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    pending_bet_id_1 int8 NULL,
    pending_bet_id_2 int8 NULL,
    odds_1 double precision NOT NULL,
    odds_2 double precision NOT NULL,
    wager_amount_1 double precision NOT NULL,
    wager_amount_2 double precision NOT NULL,
    created_date_time timestamp NOT NULL,
    fee_id_1 int8 NULL,
    fee_id_2 int8 NULL,
    matched_bet_state_id int8 NULL,
    CONSTRAINT matched_bets_pk PRIMARY KEY (matched_bet_id)
);