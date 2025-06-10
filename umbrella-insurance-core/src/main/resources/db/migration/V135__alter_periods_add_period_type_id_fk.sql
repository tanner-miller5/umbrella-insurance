ALTER TABLE public.periods ADD CONSTRAINT
periods_period_type_id_fk FOREIGN KEY 
(period_type_id) REFERENCES public.period_types(period_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;