ALTER TABLE public.conferences ADD CONSTRAINT
conferences_league_id_fk FOREIGN KEY
(league_id) REFERENCES public.leagues(league_id)
ON DELETE SET NULL ON UPDATE SET NULL;