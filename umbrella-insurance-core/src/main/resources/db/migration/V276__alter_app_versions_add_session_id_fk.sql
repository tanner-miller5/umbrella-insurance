ALTER TABLE public.app_versions ADD CONSTRAINT
app_versions_session_id_fk FOREIGN KEY
(session_id) REFERENCES public.sessions(session_id)
ON DELETE SET NULL ON UPDATE SET NULL;