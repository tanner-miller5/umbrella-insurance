CREATE TABLE IF NOT EXISTS public.application_roles (
    application_role_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    application_role_name varchar NOT NULL,
    CONSTRAINT application_roles_pk PRIMARY KEY (application_role_id),
    CONSTRAINT application_roles_unique UNIQUE (application_role_name)
);