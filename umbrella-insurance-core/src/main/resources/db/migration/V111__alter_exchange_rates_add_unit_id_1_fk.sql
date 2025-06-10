ALTER TABLE public.exchange_rates ADD CONSTRAINT 
exchange_rates_unit_id_1_fk FOREIGN KEY (unit_id_1) 
REFERENCES public.units(unit_id) 
ON DELETE SET NULL ON UPDATE SET NULL;