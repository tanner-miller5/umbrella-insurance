ALTER TABLE public.team_records ADD CONSTRAINT
team_records_season_id_fk FOREIGN KEY 
(season_id) REFERENCES public.seasons(season_id) 
ON DELETE SET NULL ON UPDATE SET NULL;