-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION atc;

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
-- DROP SEQUENCE public.product_details_seq;

CREATE SEQUENCE public.product_details_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.product_seq;

CREATE SEQUENCE public.product_seq
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

-- DROP TABLE category;

CREATE TABLE category (
	id int4 NOT NULL,
	"name" varchar NOT NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id),
	CONSTRAINT cgy_name_unq UNIQUE (name)
);


-- public.customer definition

-- Drop table

-- DROP TABLE customer;

CREATE TABLE customer (
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

-- DROP TABLE discount;

CREATE TABLE discount (
	id int4 NOT NULL,
	discount_name varchar NULL,
	discount numeric NULL,
	discount_type varchar NOT NULL DEFAULT 'relative'::character varying,
	discount_expiry timestamp NOT NULL DEFAULT to_timestamp('1970-01-01 00:00:00'::text, 'yyyy-MM-dd hh24:mi:ss'::text),
	status varchar NOT NULL DEFAULT 'active'::character varying,
	CONSTRAINT discount_pkey PRIMARY KEY (id)
);


-- public.props definition

-- Drop table

-- DROP TABLE props;

CREATE TABLE props (
	"key" varchar NULL,
	value varchar NULL
);


-- public.unit definition

-- Drop table

-- DROP TABLE unit;

CREATE TABLE unit (
	id int4 NOT NULL,
	unit_description varchar NULL,
	unit_size numeric NULL,
	CONSTRAINT unit_pkey PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE users;

CREATE TABLE users (
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

-- DROP TABLE invoice;

CREATE TABLE invoice (
	id int4 NOT NULL,
	"type" varchar NULL DEFAULT 'invoice'::character varying,
	create_ts timestamp NOT NULL DEFAULT now(),
	customer_id int4 NULL,
	user_id int4 NOT NULL,
	CONSTRAINT invoice_pkey PRIMARY KEY (id),
	CONSTRAINT invoice_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT invoice_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);


-- public.product definition

-- Drop table

-- DROP TABLE product;

CREATE TABLE product (
	id int4 NOT NULL,
	"name" varchar NULL,
	product_invoice_name varchar NULL,
	category_id int4 NULL,
	create_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT product_name_ctg_idunq UNIQUE (name, category_id),
	CONSTRAINT product_pkey PRIMARY KEY (id),
	CONSTRAINT product_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(id)
);


-- public.product_details definition

-- Drop table

-- DROP TABLE product_details;

CREATE TABLE product_details (
	id int4 NOT NULL,
	product_price numeric NULL,
	product_tax numeric NULL,
	product_id int4 NOT NULL,
	unit_id int4 NOT NULL,
	discount_id int4 NOT NULL,
	product_loc varchar NULL,
	update_ts timestamp NOT NULL DEFAULT now(),
	CONSTRAINT product_details_pkey PRIMARY KEY (id),
	CONSTRAINT product_details_discount_id_fkey FOREIGN KEY (discount_id) REFERENCES discount(id),
	CONSTRAINT product_details_product_id_fkey FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT product_details_unit_id_fkey FOREIGN KEY (unit_id) REFERENCES unit(id)
);


-- public.invoice_detail definition

-- Drop table

-- DROP TABLE invoice_detail;

CREATE TABLE invoice_detail (
	id int4 NOT NULL,
	invoice_id int4 NOT NULL,
	product_details_id int4 NOT NULL,
	tax numeric NULL,
	discount numeric NULL,
	total_amount numeric NOT NULL,
	CONSTRAINT invoice_detail_pkey PRIMARY KEY (id),
	CONSTRAINT invoice_detail_invoice_id_fkey FOREIGN KEY (invoice_id) REFERENCES invoice(id),
	CONSTRAINT invoice_detail_product_details_id_fkey FOREIGN KEY (product_details_id) REFERENCES product_details(id)
);
