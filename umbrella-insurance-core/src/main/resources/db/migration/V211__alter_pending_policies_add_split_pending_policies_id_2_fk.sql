ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_split_pending_id_2_fk FOREIGN KEY
(split_pending_policy_id_2) REFERENCES public.pending_policies(pending_policy_id)
ON DELETE SET NULL ON UPDATE SET NULL;