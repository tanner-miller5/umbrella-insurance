ALTER TABLE public.periods ADD CONSTRAINT
periods_period_status_id_fk FOREIGN KEY 
(period_status_id) REFERENCES public.period_statuses(period_status_id) 
ON DELETE SET NULL ON UPDATE SET NULL;