ALTER TABLE public.employees ADD CONSTRAINT
employees_person_id_fk FOREIGN KEY 
(person_id) REFERENCES public.people(person_id) 
ON DELETE SET NULL ON UPDATE SET NULL;