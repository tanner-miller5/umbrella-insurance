ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_pending_policy_type_id_fk FOREIGN KEY
(pending_policy_type_id) REFERENCES public.pending_policy_types(pending_policy_type_id)
ON DELETE SET NULL ON UPDATE SET NULL;