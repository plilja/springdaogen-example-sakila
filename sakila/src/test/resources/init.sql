

CREATE TABLE public.address (
address_id IDENTITY NOT NULL,
address VARCHAR(50) NOT NULL,
address2 VARCHAR(50),
city_id INTEGER NOT NULL,
district VARCHAR(20) NOT NULL,
last_update TIMESTAMP NOT NULL,
phone VARCHAR(20) NOT NULL,
postal_code VARCHAR(10),
PRIMARY KEY(address_id));

CREATE TABLE public.film (
film_id IDENTITY NOT NULL,
description CLOB,
fulltext VARCHAR(2147483647) NOT NULL,
language_id INTEGER NOT NULL,
last_update TIMESTAMP NOT NULL,
length INTEGER,
original_language_id INTEGER,
rating CLOB,
release_year VARCHAR(2147483647),
rental_duration INTEGER NOT NULL,
rental_rate DECIMAL(4, 2) NOT NULL,
replacement_cost DECIMAL(5, 2) NOT NULL,
special_features VARCHAR(2147483647),
title VARCHAR(255) NOT NULL,
PRIMARY KEY(film_id));

CREATE TABLE public.staff (
staff_id IDENTITY NOT NULL,
active BOOLEAN NOT NULL,
address_id INTEGER NOT NULL,
email VARCHAR(50),
first_name VARCHAR(45) NOT NULL,
last_name VARCHAR(45) NOT NULL,
last_update TIMESTAMP NOT NULL,
password VARCHAR(40),
picture BLOB,
store_id INTEGER NOT NULL,
username VARCHAR(16) NOT NULL,
PRIMARY KEY(staff_id));

CREATE TABLE public.category (
category_id IDENTITY NOT NULL,
last_update TIMESTAMP NOT NULL,
name VARCHAR(25) NOT NULL,
PRIMARY KEY(category_id));

CREATE TABLE public.inventory (
inventory_id IDENTITY NOT NULL,
film_id INTEGER NOT NULL,
last_update TIMESTAMP NOT NULL,
store_id INTEGER NOT NULL,
PRIMARY KEY(inventory_id));

CREATE TABLE public.rental (
rental_id IDENTITY NOT NULL,
customer_id INTEGER NOT NULL,
inventory_id INTEGER NOT NULL,
last_update TIMESTAMP NOT NULL,
rental_date TIMESTAMP NOT NULL,
return_date TIMESTAMP,
staff_id INTEGER NOT NULL,
PRIMARY KEY(rental_id));

CREATE TABLE public.actor (
actor_id IDENTITY NOT NULL,
first_name VARCHAR(45) NOT NULL,
last_name VARCHAR(45) NOT NULL,
last_update TIMESTAMP NOT NULL,
PRIMARY KEY(actor_id));

CREATE TABLE public.city (
city_id IDENTITY NOT NULL,
city VARCHAR(50) NOT NULL,
country_id INTEGER NOT NULL,
last_update TIMESTAMP NOT NULL,
PRIMARY KEY(city_id));

CREATE TABLE public.language (
language_id IDENTITY NOT NULL,
last_update TIMESTAMP NOT NULL,
name VARCHAR(20) NOT NULL,
PRIMARY KEY(language_id));

CREATE TABLE public.payment (
payment_id IDENTITY NOT NULL,
amount DECIMAL(5, 2) NOT NULL,
customer_id INTEGER NOT NULL,
payment_date TIMESTAMP NOT NULL,
rental_id INTEGER NOT NULL,
staff_id INTEGER NOT NULL,
PRIMARY KEY(payment_id));

CREATE TABLE public.store (
store_id IDENTITY NOT NULL,
address_id INTEGER NOT NULL,
last_update TIMESTAMP NOT NULL,
manager_staff_id INTEGER NOT NULL,
PRIMARY KEY(store_id));

CREATE TABLE public.customer (
customer_id IDENTITY NOT NULL,
active INTEGER,
activebool BOOLEAN NOT NULL,
address_id INTEGER NOT NULL,
create_date DATE NOT NULL,
email VARCHAR(50),
first_name VARCHAR(45) NOT NULL,
last_name VARCHAR(45) NOT NULL,
last_update TIMESTAMP,
store_id INTEGER NOT NULL,
PRIMARY KEY(customer_id));

CREATE TABLE public.country (
country_id IDENTITY NOT NULL,
country VARCHAR(50) NOT NULL,
last_update TIMESTAMP NOT NULL,
PRIMARY KEY(country_id));

ALTER TABLE public.address
ADD FOREIGN KEY (city_id)
REFERENCES public.city(city_id);
ALTER TABLE public.film
ADD FOREIGN KEY (language_id)
REFERENCES public.language(language_id);
ALTER TABLE public.film
ADD FOREIGN KEY (original_language_id)
REFERENCES public.language(language_id);
ALTER TABLE public.staff
ADD FOREIGN KEY (address_id)
REFERENCES public.address(address_id);
ALTER TABLE public.staff
ADD FOREIGN KEY (store_id)
REFERENCES public.store(store_id);
ALTER TABLE public.inventory
ADD FOREIGN KEY (film_id)
REFERENCES public.film(film_id);
ALTER TABLE public.inventory
ADD FOREIGN KEY (store_id)
REFERENCES public.store(store_id);
ALTER TABLE public.rental
ADD FOREIGN KEY (customer_id)
REFERENCES public.customer(customer_id);
ALTER TABLE public.rental
ADD FOREIGN KEY (inventory_id)
REFERENCES public.inventory(inventory_id);
ALTER TABLE public.rental
ADD FOREIGN KEY (staff_id)
REFERENCES public.staff(staff_id);
ALTER TABLE public.city
ADD FOREIGN KEY (country_id)
REFERENCES public.country(country_id);
ALTER TABLE public.payment
ADD FOREIGN KEY (customer_id)
REFERENCES public.customer(customer_id);
ALTER TABLE public.payment
ADD FOREIGN KEY (rental_id)
REFERENCES public.rental(rental_id);
ALTER TABLE public.payment
ADD FOREIGN KEY (staff_id)
REFERENCES public.staff(staff_id);
ALTER TABLE public.store
ADD FOREIGN KEY (address_id)
REFERENCES public.address(address_id);
ALTER TABLE public.store
ADD FOREIGN KEY (manager_staff_id)
REFERENCES public.staff(staff_id);
ALTER TABLE public.customer
ADD FOREIGN KEY (address_id)
REFERENCES public.address(address_id);
ALTER TABLE public.customer
ADD FOREIGN KEY (store_id)
REFERENCES public.store(store_id);

