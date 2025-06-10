ALTER TABLE public.cart_item_relationships ADD CONSTRAINT
cart_item_relationships_item_id_fk FOREIGN KEY
(item_id) REFERENCES public.items(item_id)
ON DELETE SET NULL ON UPDATE SET NULL;