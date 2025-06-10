ALTER TABLE public.locations ADD CONSTRAINT
locations_city_id_fk FOREIGN KEY 
(city_id) REFERENCES public.cities(city_id) 
ON DELETE SET NULL ON UPDATE SET NULL;