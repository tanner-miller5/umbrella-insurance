ALTER TABLE public.exchange_rates ADD CONSTRAINT 
exchange_rates_unit_id_2_fk FOREIGN KEY (unit_id_2) 
REFERENCES public.units(unit_id) 
ON DELETE SET NULL ON UPDATE SET NULL;