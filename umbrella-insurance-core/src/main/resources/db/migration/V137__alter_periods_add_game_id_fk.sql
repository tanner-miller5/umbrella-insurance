ALTER TABLE public.periods ADD CONSTRAINT
periods_game_id_fk FOREIGN KEY 
(game_id) REFERENCES public.games(game_id) 
ON DELETE SET NULL ON UPDATE SET NULL;