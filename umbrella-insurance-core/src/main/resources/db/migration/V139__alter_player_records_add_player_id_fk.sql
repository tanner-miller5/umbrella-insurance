ALTER TABLE public.player_records ADD CONSTRAINT
player_records_player_id_fk FOREIGN KEY 
(player_id) REFERENCES public.players(player_id) 
ON DELETE SET NULL ON UPDATE SET NULL;