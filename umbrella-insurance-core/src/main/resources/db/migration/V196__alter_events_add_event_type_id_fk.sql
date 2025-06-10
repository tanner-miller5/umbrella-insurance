ALTER TABLE public.events ADD CONSTRAINT
events_event_type_id_fk FOREIGN KEY 
(event_type_id) REFERENCES public.event_types(event_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;