


DROP SCHEMA full-stack-ecommerce;
CREATE SCHEMA full-stack-ecommerce;
USE full-stack-ecommerce;


set foreign_key_checks=0;
drop table customer,customer_cart,cart_item,address,card,orders,order_item,product,product_category,coupan_code,authentication_with_otp;


create table payment (
   txn_id varchar(255),
   email varchar(255),
   name varchar(255),
   phone_no varchar(255),
   contact_no varchar(255),
   product_info varchar(255), 
   amount double precision,
   payment_status varchar(255),
   payment_date date,
   pay_id varchar(255),
   mode varchar(255), 
   primary key (txn_id)
);

CREATE TABLE customer(
	phone_no VARCHAR(50),
	name VARCHAR(50) NOT NULL,
	password VARCHAR(70),
	cart_id BIGINT,
	constraint EK_CUSTOMER_EMAIL_ID_PK primary key ( phone_no )
);



CREATE TABLE customer_cart(
	id BIGINT auto_increment,
	total_quantity INT DEFAULT 0,
	cart_price DECIMAL(12,2) DEFAULT 0.0 ,
	total_discount_per DECIMAL(12,2) DEFAULT 0.0,
	total_price DECIMAL(12,2) DEFAULT 0.0,
	coupan_discount_price DECIMAL(12,2) DEFAULT 0.0,
	constraint EK_CART_ID_PK primary key ( id )
);

CREATE TABLE authentication_with_otp (
	phone_no VARCHAR(50),
	otp VARCHAR(50),
	time DATETIME ,
	constraint authentication_with_otp_pk primary key ( phone_no )
);
CREATE TABLE cart_item(
	id BIGINT auto_increment,
	cart_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	quantity BIGINT NOT NULL,
	constraint EK_CART_ITEM_ID_PK primary key ( id )
);

CREATE TABLE address (
	id BIGINT  NOT NULL auto_increment,
	name VARCHAR(50) NOT NULL,
	contact_number VARCHAR(10) NOT NULL,
	adress VARCHAR (500) NOT NULL,
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL,
	pin_code VARCHAR(10) NOT NULL,
    customer_phone_no VARCHAR(50),
	constraint EK_ADDRESS_ID_PK primary key ( id )
);

CREATE TABLE shipping_address (
	id BIGINT  NOT NULL auto_increment,
	name VARCHAR(50) NOT NULL,
	contact_number VARCHAR(10) NOT NULL,
	adress VARCHAR (500) NOT NULL,
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL,
	pin_code VARCHAR(10) NOT NULL,
	constraint EK_ADDRESS_ID_PK primary key ( id )
);

CREATE TABLE card( 
	id BIGINT auto_increment,
	card_type VARCHAR(11) NOT NULL,
	card_number VARCHAR(16) NOT NULL,
	cvv VARCHAR(70) NOT NULL,
	expiry_date DATETIME NOT NULL,
	name_on_card VARCHAR(50) NOT NULL,
    customer_phone_no VARCHAR(50),
	constraint EK_CARD_ID_PK primary key ( id )
);

CREATE TABLE orders (
	id BIGINT NOT NULL auto_increment,
	order_number VARCHAR(100) NOT NULL,
	cart_id BIGINT NOT NULL,
	date_created DATETIME NOT NULL,
	total_price DECIMAL(12,2) NOT NULL,
	shipping_address_id BIGINT NOT NULL,
	status VARCHAR(20) NOT NULL,
	payment_through VARCHAR(20) NOT NULL, 
	date_of_delivery DATETIME,
	time_of_delivery VARCHAR(20),
    customer_phone_no VARCHAR(50),
    txn_id VARCHAR(50),
	constraint EK_ORDER_ID_PK primary key (id)
);



CREATE TABLE order_item(
	id BIGINT auto_increment,
	order_id BIGINT NOT NULL,
	product_id BIGINT NOT NULL,
	constraint EK_ORDER_ITEM_ID_PK primary key ( id )
);

CREATE TABLE coupan_code(
	id BIGINT auto_increment,
	code VARCHAR(50) NOT NULL,
	discount DECIMAL(13,2) NOT NULL,
	customer_phone_no VARCHAR(50),
    constraint coupan_code_pk primary key ( id )

);
-- -----------------------------------------------------
-- Table `full-stack-ecommerce`.`product_category`
-- -----------------------------------------------------
CREATE TABLE product_category (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table `full-stack-ecommerce`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS product (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  sku VARCHAR(255) DEFAULT NULL,
  name VARCHAR(255) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  unit_price DECIMAL(13,2) DEFAULT NULL,
  product_discount DECIMAL(13,2) DEFAULT 0.0,
  image_url VARCHAR(255) DEFAULT NULL,
  active BIT DEFAULT 1,
  units_in_stock INT(11) DEFAULT NULL,
  date_created DATETIME(6) DEFAULT NULL,
  last_updated DATETIME(6) DEFAULT NULL,
  category_id BIGINT(20) NOT NULL,
  PRIMARY KEY (id)
) ;


ALTER TABLE cart_item ADD CONSTRAINT cart_item_fk FOREIGN KEY ( product_id ) REFERENCES product( id );

ALTER TABLE cart_item ADD CONSTRAINT cart_item1_fk FOREIGN KEY ( cart_id ) REFERENCES customer_cart( id );

ALTER TABLE customer ADD CONSTRAINT customer_cart_id FOREIGN KEY ( cart_id ) REFERENCES customer_cart( id );

ALTER TABLE address ADD CONSTRAINT address_fk FOREIGN KEY ( customer_phone_no ) REFERENCES customer( phone_no );

ALTER TABLE card ADD CONSTRAINT card_fk FOREIGN KEY ( customer_phone_no ) REFERENCES customer( phone_no );

ALTER TABLE coupan_code ADD CONSTRAINT coupan_code_fk FOREIGN KEY ( customer_phone_no ) REFERENCES customer( phone_no );

ALTER TABLE orders ADD CONSTRAINT order_id1_address_fk FOREIGN KEY ( shipping_address_id ) REFERENCES address( id );

ALTER TABLE orders ADD CONSTRAINT order_id1_email_fk FOREIGN KEY ( customer_phone_no ) REFERENCES customer( phone_no );

ALTER TABLE order_item ADD CONSTRAINT order_id1_item_email_fk FOREIGN KEY ( order_id ) REFERENCES orders( id );

ALTER TABLE order_item ADD CONSTRAINT order_item_product_fk FOREIGN KEY ( product_id ) REFERENCES product( id );

ALTER TABLE product ADD CONSTRAINT product_cafk FOREIGN KEY ( category_id ) REFERENCES product_category( id );


-- -----------------------------------------------------
-- Add sample data
-- -----------------------------------------------------

INSERT INTO product_category(category_name) VALUES ('BOOKS');
INSERT INTO product_category(category_name) VALUES ('Coffee Mugs');
INSERT INTO product_category(category_name) VALUES ('Mouse Pads');
INSERT INTO product_category(category_name) VALUES ('Luggage Tags');

-- coupan code

INSERT INTO coupan_code(code,discount,customer_phone_no) VALUES ('FirstOrder',5,'7417233221');


-- Books 

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1001.png'
,1,100,19.99,1,NOW(),5);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1000.png'
,1,100,29.99,1,NOW(),6);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/books/book-luv2code-1002.png'
,1,100,24.99,1,NOW(),7);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/books/book-luv2code-1003.png'
,1,100,29.99,1,NOW(),8);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/books/book-luv2code-1004.png'
,1,100,24.99,1,NOW(),9);


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/books/book-luv2code-1005.png'
,1,100,19.99,1,NOW(),10);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created,product_discount)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1006.png'
,1,100,29.99,1,NOW(),14);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/books/book-luv2code-1007.png'
,1,100,24.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/books/book-luv2code-1008.png'
,1,100,29.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/books/book-luv2code-1009.png'
,1,100,24.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1010.png'
,1,100,19.99,1,NOW(),);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1011.png'
,1,100,29.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/books/book-luv2code-1012.png'
,1,100,24.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/books/book-luv2code-1013.png'
,1,100,29.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/books/book-luv2code-1014.png'
,1,100,24.99,1,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/books/book-luv2code-1014.png'
,1,100,19.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/books/book-luv2code-1016.png'
,1,100,29.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/books/book-luv2code-1017.png'
,1,100,24.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/books/book-luv2code-1018.png'
,1,100,29.99,1,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/books/book-luv2code-1019.png'
,1,100,24.99,1,NOW());


-- coffe mugs


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1000.png'
,1,100,19.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1001.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/coffeemugs/coffeemug-luv2code-1002.png'
,1,100,24.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/coffeemugs/coffeemug-luv2code-1003.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/coffeemugs/coffeemug-luv2code-1004.png'
,1,100,24.99,2,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/coffeemugs/coffeemug-luv2code-1005.png'
,1,100,19.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1006.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/coffeemugs/coffeemug-luv2code-1007.png'
,1,100,24.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/coffeemugs/coffeemug-luv2code-1008.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/coffeemugs/coffeemug-luv2code-1009.png'
,1,100,24.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1010.png'
,1,100,19.99,2,NOW(),);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1011.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/coffeemugs/coffeemug-luv2code-1012.png'
,1,100,24.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/coffeemugs/coffeemug-luv2code-1013.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/coffeemugs/coffeemug-luv2code-1014.png'
,1,100,24.99,2,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/coffeemugs/coffeemug-luv2code-1014.png'
,1,100,19.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/coffeemugs/coffeemug-luv2code-1016.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/coffeemugs/coffeemug-luv2code-1017.png'
,1,100,24.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/coffeemugs/coffeemug-luv2code-1018.png'
,1,100,29.99,2,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/coffeemugs/coffeemug-luv2code-1019.png'
,1,100,24.99,2,NOW());

--  luggage Tags

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1001.png'
,1,100,19.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1000.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/luggagetags/luggagetag-luv2code-1002.png'
,1,100,24.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/luggagetags/luggagetag-luv2code-1003.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/luggagetags/luggagetag-luv2code-1004.png'
,1,100,24.99,3,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/luggagetags/luggagetag-luv2code-1005.png'
,1,100,19.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1006.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/luggagetags/luggagetag-luv2code-1007.png'
,1,100,24.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/luggagetags/luggagetag-luv2code-1008.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/luggagetags/luggagetag-luv2code-1009.png'
,1,100,24.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1010.png'
,1,100,19.99,3,NOW(),);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1011.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/luggagetags/luggagetag-luv2code-1012.png'
,1,100,24.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/luggagetags/luggagetag-luv2code-1013.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/luggagetags/luggagetag-luv2code-1014.png'
,1,100,24.99,3,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/luggagetags/luggagetag-luv2code-1014.png'
,1,100,19.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/luggagetags/luggagetag-luv2code-1016.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/luggagetags/luggagetag-luv2code-1017.png'
,1,100,24.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/luggagetags/luggagetag-luv2code-1018.png'
,1,100,29.99,3,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/luggagetags/luggagetag-luv2code-1019.png'
,1,100,24.99,3,NOW());

-- mouse pads;

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1001.png'
,1,100,19.99,4,NOW(),);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1000.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/mousepads/mousepad-luv2code-1002.png'
,1,100,24.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/mousepads/mousepad-luv2code-1003.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/mousepads/mousepad-luv2code-1004.png'
,1,100,24.99,4,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/mousepads/mousepad-luv2code-1005.png'
,1,100,19.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1006.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/mousepads/mousepad-luv2code-1007.png'
,1,100,24.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/mousepads/mousepad-luv2code-1008.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/mousepads/mousepad-luv2code-1009.png'
,1,100,24.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1010.png'
,1,100,19.99,4,NOW(),);

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1011.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/mousepads/mousepad-luv2code-1012.png'
,1,100,24.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/mousepads/mousepad-luv2code-1013.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/mousepads/mousepad-luv2code-1014.png'
,1,100,24.99,4,NOW());


INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
'assets/images/products/mousepads/mousepad-luv2code-1014.png'
,1,100,19.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
'assets/images/products/mousepads/mousepad-luv2code-1016.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
'assets/images/products/mousepads/mousepad-luv2code-1017.png'
,1,100,24.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
'assets/images/products/mousepads/mousepad-luv2code-1018.png'
,1,100,29.99,4,NOW());

INSERT INTO product (sku, name, description, image_url, active, units_in_stock,
unit_price, category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
'assets/images/products/mousepads/mousepad-luv2code-1019.png'
,1,100,24.99,4,NOW());



