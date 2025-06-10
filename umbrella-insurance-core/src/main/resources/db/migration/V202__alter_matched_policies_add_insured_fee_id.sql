ALTER TABLE public.matched_policies ADD CONSTRAINT
matched_policies_insured_fee_id_fk FOREIGN KEY 
(insured_fee_id) REFERENCES public.fees(fee_id) 
ON DELETE SET NULL ON UPDATE SET NULL;