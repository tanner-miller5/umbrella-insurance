CREATE TABLE IF NOT EXISTS public.cart_item_relationships (
    cart_item_relationship_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    cart_id int8 NULL,
    item_id int8 NULL,
    quantity int8 NULL,
    CONSTRAINT cart_item_relationships_pk PRIMARY KEY (cart_item_relationship_id),
    CONSTRAINT cart_item_relationships_unique UNIQUE (cart_id, item_id)
);