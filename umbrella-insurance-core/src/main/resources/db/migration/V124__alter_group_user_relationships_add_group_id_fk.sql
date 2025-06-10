ALTER TABLE public.group_user_relationships ADD CONSTRAINT
user_group_relationships_group_id_fk FOREIGN KEY 
(group_id) REFERENCES public.groups(group_id) 
ON DELETE SET NULL ON UPDATE SET NULL;