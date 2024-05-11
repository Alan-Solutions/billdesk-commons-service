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
-- DROP SEQUENCE public.customer_id_seq;

CREATE SEQUENCE public.customer_id_seq
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
-- DROP SEQUENCE public.invoice_detail_id_seq;

CREATE SEQUENCE public.invoice_detail_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.invoice_id_seq;

CREATE SEQUENCE public.invoice_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.product_details_id_seq;

CREATE SEQUENCE public.product_details_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
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
	NO CYCLE;
-- DROP SEQUENCE public.users_id_seq1;

CREATE SEQUENCE public.users_id_seq1
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
	"name" varchar(255) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id)
);


-- public.customer definition

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	dob date NULL,
	id bigserial NOT NULL,
	address varchar(255) NULL,
	create_ts varchar(255) NULL,
	"name" varchar(255) NULL,
	phone _varchar NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);


-- public.discount definition

-- Drop table

-- DROP TABLE public.discount;

CREATE TABLE public.discount (
	discount float8 NULL,
	id int4 NOT NULL,
	discount_expiry timestamp(6) NULL,
	discount_name varchar(255) NULL,
	discount_type varchar(255) NULL,
	status varchar(255) NULL,
	CONSTRAINT discount_pkey PRIMARY KEY (id)
);


-- public.invoice_detail definition

-- Drop table

-- DROP TABLE public.invoice_detail;

CREATE TABLE public.invoice_detail (
	discount float4 NULL,
	tax float4 NULL,
	total_amount float8 NULL,
	id bigserial NOT NULL,
	invoice_id int8 NULL,
	product_details_id varchar(255) NULL,
	CONSTRAINT invoice_detail_pkey PRIMARY KEY (id)
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
	unit_size float8 NULL,
	unit_description varchar(255) NULL,
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
	quantity float4 NOT NULL,
	create_ts timestamp(6) NULL,
	customer_id int8 NULL,
	id bigserial NOT NULL,
	user_id int8 NULL,
	"type" varchar(255) NULL,
	CONSTRAINT invoice_customer_id_key UNIQUE (customer_id),
	CONSTRAINT invoice_pkey PRIMARY KEY (id),
	CONSTRAINT invoice_user_id_key UNIQUE (user_id),
	CONSTRAINT fk5e32ukwo9uknwhylogvta4po6 FOREIGN KEY (customer_id) REFERENCES public.customer(id),
	CONSTRAINT fkc8jotskr93810vgn75qlbusw2 FOREIGN KEY (user_id) REFERENCES public.users(id)
);


-- public.product definition

-- Drop table

-- DROP TABLE public.product;

CREATE TABLE public.product (
	category_id int4 NULL,
	id int4 NOT NULL,
	create_ts timestamp(6) NULL,
	"name" varchar(255) NULL,
	product_invoice_name varchar(255) NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id),
	CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES public.category(id)
);


-- public.product_details definition

-- Drop table

-- DROP TABLE public.product_details;

CREATE TABLE public.product_details (
	discount_id int4 NULL,
	id serial4 NOT NULL,
	product_id int4 NULL,
	product_price float4 NULL,
	product_tax float4 NULL,
	unit_id int4 NULL,
	update_ts timestamp(6) NULL,
	product_loc varchar(255) NULL,
	CONSTRAINT product_details_pkey PRIMARY KEY (id),
	CONSTRAINT fkeiohp4spahrikingreddm7n8d FOREIGN KEY (unit_id) REFERENCES public.unit(id),
	CONSTRAINT fkndgg1ab1volapr3o9ger2hb7g FOREIGN KEY (discount_id) REFERENCES public.discount(id),
	CONSTRAINT fkrhahp4f26x99lqf0kybcs79rb FOREIGN KEY (product_id) REFERENCES public.product(id)
);