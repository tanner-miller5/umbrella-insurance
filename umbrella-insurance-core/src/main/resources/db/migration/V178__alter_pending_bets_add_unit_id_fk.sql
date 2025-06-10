ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_unit_id_fk FOREIGN KEY 
(unit_id) REFERENCES public.units(unit_id) 
ON DELETE SET NULL ON UPDATE SET NULL;