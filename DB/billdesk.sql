-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION atc;

COMMENT ON SCHEMA public IS 'standard public schema';

-- DROP SEQUENCE public.category_seq;

CREATE SEQUENCE public.category_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.discount_seq;

CREATE SEQUENCE public.discount_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.unit_seq;

CREATE SEQUENCE public.unit_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.users_id_seq;

CREATE SEQUENCE public.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- public.category definition

-- Drop table

-- DROP TABLE public.category;

CREATE TABLE public.category (
	id int4 NOT NULL,
	"name" varchar NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id),
	CONSTRAINT name_unq UNIQUE (name)
);


-- public.customer definition

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	id int4 NOT NULL,
	"name" varchar NULL,
	phone _varchar NULL,
	dob date NULL,
	create_ts varchar NULL,
	address varchar NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);
CREATE INDEX cust_phone_idx ON public.customer USING gin (phone);


-- public.discount definition

-- Drop table

-- DROP TABLE public.discount;

CREATE TABLE public.discount (
	id int4 NOT NULL,
	discount_name varchar NULL,
	discount numeric NULL,
	discount_type varchar NULL,
	discount_expiry timestamp NOT NULL DEFAULT to_timestamp('1970-01-01 00:00:00'::text, 'yyyy-MM-dd hh24:mi:ss'::text),
	status varchar NOT NULL DEFAULT 'active'::character varying,
	CONSTRAINT dis_name_unq null,
	CONSTRAINT discount_pkey null
);


-- public.props definition

-- Drop table

-- DROP TABLE public.props;

CREATE TABLE public.props (
	"key" varchar NULL,
	value varchar NULL
);


-- public.unit definition

-- Drop table

-- DROP TABLE public.unit;

CREATE TABLE public.unit (
	id int4 NOT NULL,
	unit_description varchar NULL,
	unit_size numeric NULL,
	CONSTRAINT unit_pkey PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	created_ts timestamp(6) NOT NULL,
	id bigserial NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	mobile_no varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
	"role" _varchar NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);


-- public.invoice definition

-- Drop table

-- DROP TABLE public.invoice;

CREATE TABLE public.invoice (
	id int4 NOT NULL,
	"type" varchar NULL DEFAULT 'invoice'::character varying,
	create_ts timestamp NOT NULL DEFAULT now(),
	customer_id int4 NULL,
	user_id int4 NOT NULL,
	CONSTRAINT invoice_pkey PRIMARY KEY (id),
	CONSTRAINT invoice_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id),
	CONSTRAINT invoice_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);


-- public.item definition

-- Drop table

-- DROP TABLE public.item;

CREATE TABLE public.item (
	id int4 NOT NULL,
	item_name varchar NULL,
	item_invoice_name varchar NULL,
	category_id int4 NULL,
	create_ts timestamp NOT NULL DEFAULT now(),
	CONSTRAINT item_pkey PRIMARY KEY (id),
	CONSTRAINT item_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(id)
);


-- public.item_details definition

-- Drop table

-- DROP TABLE public.item_details;

CREATE TABLE public.item_details (
	id int4 NOT NULL,
	item_price numeric NULL,
	item_tax numeric NULL,
	item_id int4 NOT NULL,
	unit_id int4 NOT NULL,
	discount_id int4 NOT NULL,
	item_loc varchar NULL,
	update_ts timestamp NOT NULL DEFAULT now(),
	CONSTRAINT item_details_pkey null,
	CONSTRAINT item_details_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES public.discount(id),
	CONSTRAINT item_details_item_id_fkey FOREIGN KEY (item_id) REFERENCES public.item(id),
	CONSTRAINT item_details_unit_id_fkey FOREIGN KEY (unit_id) REFERENCES public.unit(id)
);


-- public.invoice_detail definition

-- Drop table

-- DROP TABLE public.invoice_detail;

CREATE TABLE public.invoice_detail (
	id int4 NOT NULL,
	invoice_id int4 NOT NULL,
	item_details_id int4 NOT NULL,
	tax numeric NULL,
	discount numeric NULL,
	total_amount numeric NOT NULL,
	quantity int4 NOT NULL DEFAULT 1,
	CONSTRAINT invoice_detail_pkey null,
	CONSTRAINT invoice_detail_invoice_id_fkey FOREIGN KEY (invoice_id) REFERENCES public.invoice(id),
	CONSTRAINT invoice_detail_item_details_id_fkey FOREIGN KEY (item_details_id) REFERENCES public.item_details(id)
);
