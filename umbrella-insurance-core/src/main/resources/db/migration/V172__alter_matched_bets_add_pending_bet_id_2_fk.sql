ALTER TABLE public.matched_bets ADD CONSTRAINT
matched_bets_pending_bet_id_2_fk FOREIGN KEY 
(pending_bet_id_2) REFERENCES public.pending_bets(pending_bet_id) 
ON DELETE SET NULL ON UPDATE SET NULL;