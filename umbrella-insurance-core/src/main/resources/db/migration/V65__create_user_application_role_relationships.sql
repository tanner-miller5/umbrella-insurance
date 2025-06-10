CREATE TABLE IF NOT EXISTS public.user_application_role_relationships (
    user_application_role_relationship_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_id int8 NULL,
    application_role_id int8 NULL,
    CONSTRAINT user_application_role_relationships_pk PRIMARY KEY (user_application_role_relationship_id),
    CONSTRAINT user_application_role_relationships_unique UNIQUE (user_id, application_role_id)
);