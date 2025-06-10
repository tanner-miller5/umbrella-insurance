ALTER TABLE public.locations ADD CONSTRAINT
locations_country_id_fk FOREIGN KEY 
(country_id) REFERENCES public.countries(country_id) 
ON DELETE SET NULL ON UPDATE SET NULL;