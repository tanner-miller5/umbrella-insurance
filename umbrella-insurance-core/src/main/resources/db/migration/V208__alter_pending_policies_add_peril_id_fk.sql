ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_peril_id_fk FOREIGN KEY
(peril_id) REFERENCES public.perils(peril_id)
ON DELETE SET NULL ON UPDATE SET NULL;