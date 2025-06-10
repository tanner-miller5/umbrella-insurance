CREATE TABLE IF NOT EXISTS public.faqs (
    faq_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    created_date_time timestamp NOT NULL,
    question varchar NOT NULL,
    answer varchar NOT NULL,
    faq_name varchar NOT NULL,
    session_id int8 NULL,
    CONSTRAINT faqs_pk PRIMARY KEY (faq_id),
    CONSTRAINT faqs_unique UNIQUE (faq_name)
);