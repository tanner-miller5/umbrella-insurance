ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_unit_id_fk FOREIGN KEY
(unit_id) REFERENCES public.units(unit_id)
ON DELETE SET NULL ON UPDATE SET NULL;