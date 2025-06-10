ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_pending_policy_state_id_fk FOREIGN KEY
(pending_policy_state_id) REFERENCES public.pending_policy_states(pending_policy_state_id)
ON DELETE SET NULL ON UPDATE SET NULL;