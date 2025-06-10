ALTER TABLE public.locations ADD CONSTRAINT
locations_street_address_id_fk FOREIGN KEY 
(street_address_id) REFERENCES public.street_addresses(street_address_id) 
ON DELETE SET NULL ON UPDATE SET NULL;