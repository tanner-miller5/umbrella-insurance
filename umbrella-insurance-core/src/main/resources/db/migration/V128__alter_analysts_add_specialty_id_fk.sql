ALTER TABLE public.analysts ADD CONSTRAINT
analysts_specialty_id_fk FOREIGN KEY
(specialty_id) REFERENCES public.specialties(specialty_id)
ON DELETE SET NULL ON UPDATE SET NULL;