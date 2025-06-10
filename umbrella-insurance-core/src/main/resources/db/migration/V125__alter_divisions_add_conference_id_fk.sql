ALTER TABLE public.divisions ADD CONSTRAINT
divisions_conference_id_fk FOREIGN KEY 
(conference_id) REFERENCES public.conferences(conference_id) 
ON DELETE SET NULL ON UPDATE SET NULL;