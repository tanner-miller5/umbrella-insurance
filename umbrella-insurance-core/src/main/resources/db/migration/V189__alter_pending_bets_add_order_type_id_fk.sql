ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_order_type_id_fk FOREIGN KEY
(order_type_id) REFERENCES public.order_types(order_type_id)
ON DELETE SET NULL ON UPDATE SET NULL;