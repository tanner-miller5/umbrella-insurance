ALTER TABLE public.games ADD CONSTRAINT 
games_game_status_id_fk FOREIGN KEY (game_status_id) 
REFERENCES public.game_statuses(game_status_id) 
ON DELETE SET NULL ON UPDATE SET NULL;