ALTER TABLE public.team_transactions ADD CONSTRAINT
team_transactions_team_transaction_type_id_fk FOREIGN KEY 
(team_transaction_type_id) REFERENCES public.team_transaction_types(team_transaction_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;