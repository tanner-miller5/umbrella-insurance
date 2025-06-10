ALTER TABLE public.football_player_stats ADD CONSTRAINT
football_player_stats_player_id_fk FOREIGN KEY 
(player_id) REFERENCES public.players(player_id) 
ON DELETE SET NULL ON UPDATE SET NULL;