ALTER TABLE public.teams ADD CONSTRAINT
teams_team_type_id_fk FOREIGN KEY 
(team_type_id) REFERENCES public.team_types(team_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;