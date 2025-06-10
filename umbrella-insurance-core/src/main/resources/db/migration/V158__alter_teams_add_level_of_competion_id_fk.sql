ALTER TABLE public.teams ADD CONSTRAINT
teams_level_of_competition_id_fk FOREIGN KEY 
(level_of_competition_id) REFERENCES public.level_of_competitions(level_of_competition_id) 
ON DELETE SET NULL ON UPDATE SET NULL;