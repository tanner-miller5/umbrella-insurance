CREATE TABLE IF NOT EXISTS public.equipment (
    equipment_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    equipment_name varchar NULL,
    CONSTRAINT equipment_pk PRIMARY KEY (equipment_id),
    CONSTRAINT equipment_unique UNIQUE (equipment_name)
);