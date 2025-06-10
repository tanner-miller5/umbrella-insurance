CREATE TABLE IF NOT EXISTS public.companies (
    company_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    company_name varchar NOT NULL,
    CONSTRAINT companies_pk PRIMARY KEY (company_id),
    CONSTRAINT companies_unique UNIQUE (company_name)
);