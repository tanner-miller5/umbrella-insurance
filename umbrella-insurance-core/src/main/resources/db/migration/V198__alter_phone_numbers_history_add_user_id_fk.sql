ALTER TABLE public.phone_numbers_history ADD CONSTRAINT
phone_numbers_history_user_id_fk FOREIGN KEY 
(user_id) REFERENCES public.users(user_id) 
ON DELETE SET NULL ON UPDATE SET NULL;