ALTER TABLE public.matched_policies ADD CONSTRAINT
matched_policies_matched_policy_state_id_fk FOREIGN KEY 
(matched_policy_state_id) REFERENCES public.matched_policy_states(matched_policy_state_id) 
ON DELETE SET NULL ON UPDATE SET NULL;