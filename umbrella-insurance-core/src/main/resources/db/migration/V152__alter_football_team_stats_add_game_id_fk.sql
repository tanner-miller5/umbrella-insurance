ALTER TABLE public.football_team_stats ADD CONSTRAINT
football_team_stats_game_id_fk FOREIGN KEY 
(game_id) REFERENCES public.games(game_id) 
ON DELETE SET NULL ON UPDATE SET NULL;