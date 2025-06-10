ALTER TABLE public.bots ADD CONSTRAINT
bots_analyst_id_fk FOREIGN KEY 
(analyst_id) REFERENCES public.analysts(analyst_id) 
ON DELETE SET NULL ON UPDATE SET NULL;