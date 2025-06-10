ALTER TABLE public.teams ADD CONSTRAINT
teams_game_type_id_fk FOREIGN KEY 
(game_type_id) REFERENCES public.game_types(game_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;