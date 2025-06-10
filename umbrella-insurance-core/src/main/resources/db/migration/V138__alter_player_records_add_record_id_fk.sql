ALTER TABLE public.player_records ADD CONSTRAINT
player_records_record_id_fk FOREIGN KEY 
(record_id) REFERENCES public.records(record_id) 
ON DELETE SET NULL ON UPDATE SET NULL;