ALTER TABLE public.schedules ADD CONSTRAINT
schedules_season_id_fk FOREIGN KEY 
(season_id) REFERENCES public.seasons(season_id) 
ON DELETE SET NULL ON UPDATE SET NULL;