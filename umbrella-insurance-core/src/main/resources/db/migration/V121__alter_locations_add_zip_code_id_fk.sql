ALTER TABLE public.locations ADD CONSTRAINT
locations_zip_code_id_fk FOREIGN KEY 
(zip_code_id) REFERENCES public.zip_codes(zip_code_id) 
ON DELETE SET NULL ON UPDATE SET NULL;