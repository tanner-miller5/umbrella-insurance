CREATE TABLE IF NOT EXISTS public.wire_transfers (
    wire_transfer_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    transfer_id int8 NULL,
    CONSTRAINT wire_transfers_pk PRIMARY KEY (wire_transfer_id),
    CONSTRAINT wire_transfers_unique UNIQUE (transfer_id)
);