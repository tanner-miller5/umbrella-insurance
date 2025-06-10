ALTER TABLE public.email_addresses_history ADD CONSTRAINT
email_addresses_history_user_id_fk FOREIGN KEY 
(user_id) REFERENCES public.users(user_id) 
ON DELETE SET NULL ON UPDATE SET NULL;