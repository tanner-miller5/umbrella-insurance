ALTER TABLE public.locations ADD CONSTRAINT
locations_state_id_fk FOREIGN KEY 
(state_id) REFERENCES public.states(state_id) 
ON DELETE SET NULL ON UPDATE SET NULL;