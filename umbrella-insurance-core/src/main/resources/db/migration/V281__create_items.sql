CREATE TABLE IF NOT EXISTS public.items (
    item_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    item_name varchar NOT NULL,
    price float8 NOT NULL,
    description varchar NOT NULL,
    item_image bytea NULL,
    upc varchar NOT NULL,
    unit_id int8 NULL,
    CONSTRAINT items_pk PRIMARY KEY (item_id),
    CONSTRAINT items_unique UNIQUE (item_name)
);

ALTER TABLE public.items ADD CONSTRAINT
    items_unit_id_fk FOREIGN KEY
        (unit_id) REFERENCES public.units(unit_id)
        ON DELETE SET NULL ON UPDATE SET NULL;