ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_session_id_fk FOREIGN KEY 
(session_id) REFERENCES public.sessions(session_id) 
ON DELETE SET NULL ON UPDATE SET NULL;