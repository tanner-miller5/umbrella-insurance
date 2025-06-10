ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_pending_bet_state_id_fk FOREIGN KEY 
(pending_bet_state_id) REFERENCES public.pending_bet_states(pending_bet_state_id) 
ON DELETE SET NULL ON UPDATE SET NULL;