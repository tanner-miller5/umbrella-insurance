ALTER TABLE public.pending_bets ADD CONSTRAINT
pending_bets_fee_id_fk FOREIGN KEY 
(fee_id) REFERENCES public.fees(fee_id) 
ON DELETE SET NULL ON UPDATE SET NULL;