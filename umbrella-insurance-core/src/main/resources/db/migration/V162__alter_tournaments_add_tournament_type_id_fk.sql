ALTER TABLE public.tournaments ADD CONSTRAINT
tournaments_tournament_type_id_fk FOREIGN KEY 
(tournament_type_id) REFERENCES public.tournament_types(tournament_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;