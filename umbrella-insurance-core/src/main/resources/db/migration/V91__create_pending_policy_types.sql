CREATE TABLE IF NOT EXISTS public.pending_policy_types (
    pending_policy_type_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    pending_policy_type_name varchar NOT NULL,
    CONSTRAINT pending_policy_types_pk PRIMARY KEY (pending_policy_type_id),
    CONSTRAINT pending_policy_type_name_unique UNIQUE (pending_policy_type_name)
);