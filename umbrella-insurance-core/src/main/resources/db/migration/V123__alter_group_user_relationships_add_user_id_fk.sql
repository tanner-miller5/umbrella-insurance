ALTER TABLE public.group_user_relationships ADD CONSTRAINT
user_group_relationships_user_id_fk FOREIGN KEY 
(user_id) REFERENCES public.users(user_id) 
ON DELETE SET NULL ON UPDATE SET NULL;