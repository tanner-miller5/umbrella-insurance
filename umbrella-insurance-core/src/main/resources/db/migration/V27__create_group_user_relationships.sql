CREATE TABLE IF NOT EXISTS public.group_user_relationships (
    group_user_relationship_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    group_id int8 NULL,
    user_id int8 NULL,
    CONSTRAINT group_user_relationships_pk PRIMARY KEY (group_user_relationship_id),
    CONSTRAINT group_user_relationships_unique UNIQUE (group_id, user_id)
);