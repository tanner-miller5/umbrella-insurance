ALTER TABLE public.card_transfers ADD CONSTRAINT
card_transfers_card_on_file_id_fk FOREIGN KEY
(card_on_file_id) REFERENCES public.cards_on_file(card_on_file_id)
ON DELETE SET NULL ON UPDATE SET NULL;