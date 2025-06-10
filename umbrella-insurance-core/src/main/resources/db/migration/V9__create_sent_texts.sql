CREATE TABLE IF NOT EXISTS public.sent_texts (
    sent_text_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    recipient_phone_number varchar NOT NULL,
    sender_phone_number varchar NOT NULL,
    text_message varchar NOT NULL,
    sent_date_time timestamp NOT NULL,
    CONSTRAINT sent_texts_pk PRIMARY KEY (sent_text_id)
);