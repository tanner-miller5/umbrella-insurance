CREATE TABLE IF NOT EXISTS public.app_versions (
    app_version_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    app_name varchar NOT NULL,
    app_version varchar NOT NULL,
    session_id int8 NULL,
    CONSTRAINT app_versions_pk PRIMARY KEY (app_version_id),
    CONSTRAINT app_versions_unique UNIQUE (app_name, app_version)
);