ALTER TABLE public.cart_item_relationships ADD CONSTRAINT
cart_item_relationships_cart_id_fk FOREIGN KEY
(cart_id) REFERENCES public.carts(cart_id)
ON DELETE SET NULL ON UPDATE SET NULL;