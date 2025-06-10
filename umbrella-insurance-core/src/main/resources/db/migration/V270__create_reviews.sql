CREATE TABLE IF NOT EXISTS public.reviews (
    review_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    created_date_time timestamp NOT NULL,
    subject varchar NOT NULL,
    comment varchar NOT NULL,
    rating int NOT NULL,
    session_id int8 NULL,
    frontend_app_version varchar NOT NULL,
    backend_app_version varchar NOT NULL,
    user_id int8 null,
    CONSTRAINT review_pk PRIMARY KEY (review_id),
    CONSTRAINT review_unique UNIQUE (user_id, frontend_app_version, backend_app_version)
);