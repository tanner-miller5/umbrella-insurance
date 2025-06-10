CREATE TABLE IF NOT EXISTS public.sent_emails (
    sent_email_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    content_type varchar NULL,
    email_subject varchar NOT NULL,
    email_body varchar NULL,
    recipient_email_address varchar NOT NULL,
    sender_email_address varchar NOT NULL,
    user_id int8 NULL,
    sent_date_time timestamp NOT NULL,
    CONSTRAINT sent_emails_pk PRIMARY KEY (sent_email_id),
    CONSTRAINT sent_emails_unique UNIQUE (email_subject, sent_date_time)
);