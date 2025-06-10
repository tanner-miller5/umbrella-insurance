ALTER TABLE public.verification_codes ADD CONSTRAINT
verification_codes_verification_method_id_fk FOREIGN KEY 
(verification_method_id) REFERENCES public.verification_methods(verification_method_id) 
ON DELETE SET NULL ON UPDATE SET NULL;