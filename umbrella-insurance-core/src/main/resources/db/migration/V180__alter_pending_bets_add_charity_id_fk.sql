ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_charity_id_fk FOREIGN KEY 
(charity_id) REFERENCES public.charities(charity_id) 
ON DELETE SET NULL ON UPDATE SET NULL;