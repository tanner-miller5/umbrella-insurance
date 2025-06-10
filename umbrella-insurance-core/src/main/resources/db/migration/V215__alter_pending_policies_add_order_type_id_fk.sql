ALTER TABLE public.pending_policies ADD CONSTRAINT
pending_policies_order_type_id_fk FOREIGN KEY
(order_type_id) REFERENCES public.order_types(order_type_id)
ON DELETE SET NULL ON UPDATE SET NULL;