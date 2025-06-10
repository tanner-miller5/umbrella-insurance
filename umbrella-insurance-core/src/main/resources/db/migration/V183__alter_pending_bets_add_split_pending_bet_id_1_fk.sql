ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_split_pending_bet_id_1_fk FOREIGN KEY 
(split_pending_bet_id_1) REFERENCES public.pending_bets(pending_bet_id) 
ON DELETE SET NULL ON UPDATE SET NULL;