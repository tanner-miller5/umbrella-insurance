ALTER TABLE public.sent_texts ADD CONSTRAINT 
sent_texts_user_id_fk FOREIGN KEY (user_id) 
REFERENCES public.users(user_id) 
ON DELETE SET NULL ON UPDATE SET NULL;