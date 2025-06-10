ALTER TABLE public.passwords ADD CONSTRAINT
passwords_user_id_fk FOREIGN KEY 
(user_id) REFERENCES public.users(user_id) 
ON DELETE SET NULL ON UPDATE SET NULL;