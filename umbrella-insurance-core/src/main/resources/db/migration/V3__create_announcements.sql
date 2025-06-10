CREATE TABLE IF NOT EXISTS public.announcements (
    announcement_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    created_date_time timestamp NOT NULL,
    message varchar NOT NULL,
    announcement_name varchar NOT NULL,
    session_id int8 NULL,
    CONSTRAINT announcements_pk PRIMARY KEY (announcement_id),
    CONSTRAINT announcements_unique UNIQUE (announcement_name)
);