CREATE TABLE IF NOT EXISTS public.application_privileges (
    application_privilege_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    application_privilege_name varchar NOT NULL,
    path varchar NOT NULL,
    method varchar NOT NULL,
    access varchar NOT NULL,
    CONSTRAINT application_privileges_pk PRIMARY KEY (application_privilege_id),
    CONSTRAINT application_privileges_unique UNIQUE (application_privilege_name)
);