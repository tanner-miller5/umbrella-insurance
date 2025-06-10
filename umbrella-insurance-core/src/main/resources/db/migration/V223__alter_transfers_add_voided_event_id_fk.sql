ALTER TABLE public.transfers ADD CONSTRAINT
transfers_voided_event_id_fk FOREIGN KEY
(voided_event_id) REFERENCES public.events(event_id)
ON DELETE SET NULL ON UPDATE SET NULL;