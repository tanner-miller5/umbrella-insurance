ALTER TABLE public.games ADD CONSTRAINT 
games_season_type_id_fk FOREIGN KEY (season_type_id) 
REFERENCES public.season_types(season_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;