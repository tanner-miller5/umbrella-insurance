ALTER TABLE public.teams ADD CONSTRAINT
teams_last_season_id_fk FOREIGN KEY 
(last_season_id) REFERENCES public.seasons(season_id) 
ON DELETE SET NULL ON UPDATE SET NULL;