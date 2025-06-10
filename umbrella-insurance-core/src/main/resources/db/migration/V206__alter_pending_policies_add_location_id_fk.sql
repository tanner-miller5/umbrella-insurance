ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_location_id_fk FOREIGN KEY 
(location_id) REFERENCES public.locations(location_id) 
ON DELETE SET NULL ON UPDATE SET NULL;