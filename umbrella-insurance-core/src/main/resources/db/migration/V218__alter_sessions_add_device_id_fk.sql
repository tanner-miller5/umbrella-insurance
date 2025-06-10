ALTER TABLE public.sessions ADD CONSTRAINT
sessions_device_id_fk FOREIGN KEY
(device_id) REFERENCES public.devices(id)
ON DELETE SET NULL ON UPDATE SET NULL;