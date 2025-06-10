ALTER TABLE public.team_transactions ADD CONSTRAINT
team_transactions_team_id_fk FOREIGN KEY 
(team_id) REFERENCES public.teams(team_id) 
ON DELETE SET NULL ON UPDATE SET NULL;