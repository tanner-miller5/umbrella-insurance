ALTER TABLE public.matched_bets ADD CONSTRAINT
matched_bets_fee_id_1_fk FOREIGN KEY 
(fee_id_1) REFERENCES public.fees(fee_id) 
ON DELETE SET NULL ON UPDATE SET NULL;