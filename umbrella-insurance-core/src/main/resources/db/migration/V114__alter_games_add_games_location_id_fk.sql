ALTER TABLE public.games ADD CONSTRAINT 
games_location_id_fk FOREIGN KEY (location_id) 
REFERENCES public.locations(location_id) 
ON DELETE SET NULL ON UPDATE SET NULL;