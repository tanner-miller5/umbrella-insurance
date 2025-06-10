CREATE TABLE IF NOT EXISTS public.phone_numbers_history (
    phone_number_history_id int8 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    user_Id int8 NULL,
    phone_number varchar NOT NULL,
    created_date_time timestamp NULL,
    CONSTRAINT phone_numbers_history_pk PRIMARY KEY (phone_number_history_id)
);