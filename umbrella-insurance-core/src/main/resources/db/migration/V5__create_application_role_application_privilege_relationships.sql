CREATE TABLE IF NOT EXISTS public.application_role_application_privilege_relationships (
    application_role_application_privilege_relationship_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    application_role_id int8 NULL,
    application_privilege_id int8 NULL,
    CONSTRAINT application_role_application_privilege_relationships_pk PRIMARY KEY (application_role_application_privilege_relationship_id),
    CONSTRAINT application_role_application_privilege_relationships_unique UNIQUE (application_role_id, application_privilege_id)
);