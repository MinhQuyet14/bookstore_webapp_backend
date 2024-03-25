CREATE DATABASE sc_shopapp;
use sc_shopapp;

create table users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT ''
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at datetime,
    updated_at datetime,
    is_active tinyint(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);

create table roles(
    id int PRIMARY KEY,
    name varchar(30) not null
);
alter table users add column role_id int;
alter table users add foreign key(role_id) references roles(id);
CREATE table tokens (
    id int PRIMARY KEY AUTO_INCREMENT,
    token varchar(255) unique not null,
    token_type varchar(50) not null,
    expiration_date DATETIME,
    revoked tinyint(1) not null,
    expired tinyint(1) NOT null,
    user_id int,
    foreign key (user_id) references users(id)
);
create table social_account(
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `provider` VARCHAR(20) NOT NULL COMMENT 'Name of social network provider',
    `provider_id` VARCHAR(50) not null,
    `email` varchar(150) not null,
    `name` varchar(100) not null,
    user_id int,
    foreign key (user_id) references users(id)
);

create table categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) not null DEFAULT ''
);

create table products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) not null,
    price FLOAT NOT NULL,
    url VARCHAR(300) DEFAULT '',
    description LONGTEXT DEFAULT '',
    created_at datetime,
    updated_at datetime,
    category_id int,
    foreign key (category_id) references categories(id)
);

create table orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    foreign key (user_id) references users(id),
    `fullname` VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number varchar(100) NOT NULL,
    address VARCHAR(200) not null,
    note VARCHAR(100) DEFAULT '',
    order_date datetime DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money float
);

alter table orders add column `shipping_method` VARCHAR(100);
alter table orders add column `shipping_address` VARCHAR(200);
alter table orders add column `shipping_date` DATE;
alter table orders add column `tracking_number` VARCHAR(100);
alter table orders add column `payment_method` VARCHAR(100);
alter table orders add column `active` tinyint(1);
alter table orders 
modify column status enum('pending', 'processing', 'shipped', 'delivered', 'cancelled')
comment 'Status of order';
create table order_details(
    id int PRIMARY KEY AUTO_INCREMENT,
    order_id int,
    foreign key (order_id) references orders(id),
    product_id int,
    foreign key (product_id) references products(id),
    price float, 
    number_of_products int,
    total_money float
);