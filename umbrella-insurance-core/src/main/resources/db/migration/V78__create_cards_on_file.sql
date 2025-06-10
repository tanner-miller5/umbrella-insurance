CREATE TABLE IF NOT EXISTS public.cards_on_file (
    card_on_file_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    card_number varchar NOT NULL,
    expiration_date date NOT NULL,
    cvv varchar NOT NULL,
    user_id int8 NULL,
    phone_number varchar NOT NULL,
    location_id int8 NULL,
    created_date_time timestamp NOT NULL,
    deleted_date_time timestamp NULL,
    is_deleted bool NULL,
    CONSTRAINT cards_on_file_pk PRIMARY KEY (card_on_file_id),
    CONSTRAINT cards_on_file_unique UNIQUE (card_number)
);