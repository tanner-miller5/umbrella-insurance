ALTER TABLE public.card_transfers ADD CONSTRAINT
card_transfers_transfer_id_fk FOREIGN KEY
(transfer_id) REFERENCES public.transfers(transfer_id)
ON DELETE SET NULL ON UPDATE SET NULL;