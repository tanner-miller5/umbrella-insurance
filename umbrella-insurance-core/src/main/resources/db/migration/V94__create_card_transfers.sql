CREATE TABLE IF NOT EXISTS public.card_transfers (
    card_transfer_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    card_on_file_id int8 NULL,
    transfer_id int8 NULL,
    CONSTRAINT card_transfers_pk PRIMARY KEY (card_transfer_id),
    CONSTRAINT card_transfers_unique UNIQUE (transfer_id)
);