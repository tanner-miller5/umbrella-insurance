ALTER TABLE public.sessions ADD CONSTRAINT
sessions_verification_code_id_fk FOREIGN KEY
(verification_code_id) REFERENCES public.verification_codes(verification_code_id) 
ON DELETE SET NULL ON UPDATE SET NULL;