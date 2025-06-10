ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_bet_type_id_fk FOREIGN KEY
(bet_type_id) REFERENCES public.bet_types(bet_type_id)
ON DELETE SET NULL ON UPDATE SET NULL;