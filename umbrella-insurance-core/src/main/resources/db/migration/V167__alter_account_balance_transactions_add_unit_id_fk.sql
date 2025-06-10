ALTER TABLE public.account_balance_transactions ADD CONSTRAINT
account_balance_transactions_unit_id_fk FOREIGN KEY 
(unit_id) REFERENCES public.units(unit_id) 
ON DELETE SET NULL ON UPDATE SET NULL;