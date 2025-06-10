ALTER TABLE public.user_application_role_relationships ADD CONSTRAINT
user_application_role_relationships_application_role_id_fk FOREIGN KEY 
(application_role_id) REFERENCES public.application_roles(application_role_id) 
ON DELETE SET NULL ON UPDATE SET NULL;