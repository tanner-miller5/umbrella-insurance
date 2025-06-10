ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_original_pending_id_fk FOREIGN KEY
(original_pending_policy_id) REFERENCES public.pending_policies(pending_policy_id)
ON DELETE SET NULL ON UPDATE SET NULL;