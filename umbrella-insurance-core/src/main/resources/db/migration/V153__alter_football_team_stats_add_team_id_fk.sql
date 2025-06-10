ALTER TABLE public.football_team_stats ADD CONSTRAINT
football_team_stats_team_id_fk FOREIGN KEY 
(team_id) REFERENCES public.teams(team_id) 
ON DELETE SET NULL ON UPDATE SET NULL;