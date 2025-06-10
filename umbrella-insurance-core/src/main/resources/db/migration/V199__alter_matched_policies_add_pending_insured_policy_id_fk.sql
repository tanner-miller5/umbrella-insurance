ALTER TABLE public.matched_policies ADD CONSTRAINT
matched_policies_pending_insured_policy_id_fk FOREIGN KEY 
(pending_insured_policy_id) REFERENCES public.pending_policies(pending_policy_id) 
ON DELETE SET NULL ON UPDATE SET NULL;