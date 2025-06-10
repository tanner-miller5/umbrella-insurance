ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_split_pending_id_1_fk FOREIGN KEY
(split_pending_policy_id_1) REFERENCES public.pending_policies(pending_policy_id)
ON DELETE SET NULL ON UPDATE SET NULL;