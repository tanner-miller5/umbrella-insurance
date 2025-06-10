ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_original_pending_id_fk FOREIGN KEY
(original_pending_bet_id) REFERENCES public.pending_bets(pending_bet_id)
ON DELETE SET NULL ON UPDATE SET NULL;