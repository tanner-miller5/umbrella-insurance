ALTER TABLE public.football_player_stats ADD CONSTRAINT
football_player_stats_game_id_fk FOREIGN KEY 
(game_id) REFERENCES public.games(game_id) 
ON DELETE SET NULL ON UPDATE SET NULL;