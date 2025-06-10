CREATE TABLE IF NOT EXISTS public.verification_methods (
    verification_method_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    verification_method_name varchar NULL,
    CONSTRAINT verification_method_pk PRIMARY KEY (verification_method_id),
    CONSTRAINT verification_method_unique UNIQUE (verification_method_name)
);