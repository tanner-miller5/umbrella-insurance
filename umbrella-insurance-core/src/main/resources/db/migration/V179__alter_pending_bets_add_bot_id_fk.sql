ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_bot_id_fk FOREIGN KEY 
(bot_id) REFERENCES public.bots(bot_id) 
ON DELETE SET NULL ON UPDATE SET NULL;