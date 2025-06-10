ALTER TABLE public.account_balances ADD CONSTRAINT
account_balances_account_balance_type_id_fk FOREIGN KEY 
(account_balance_type_id) REFERENCES public.account_balance_types(account_balance_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;