ALTER TABLE public.teams ADD CONSTRAINT
teams_first_season_id_fk FOREIGN KEY 
(first_season_id) REFERENCES public.seasons(season_id) 
ON DELETE SET NULL ON UPDATE SET NULL;