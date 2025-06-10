ALTER TABLE public.matched_bets ADD CONSTRAINT
matched_bets_matched_bet_state_id_fk FOREIGN KEY 
(matched_bet_state_id) REFERENCES public.matched_bet_states(matched_bet_state_id) 
ON DELETE SET NULL ON UPDATE SET NULL;