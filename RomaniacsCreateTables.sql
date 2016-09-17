drop database  if exists romaniacs;
create database romaniacs;

drop table if exists game;
CREATE TABLE game (
    game_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45),
    price DECIMAL(10 , 2 ),
    type ENUM('sport', 'shooter', 'rpg', 'adventure'),
    platform ENUM('pc', 'ps', 'xbox'),
    release_date DATE,
    age_rating INT,
    stock INT,
    system_requirements ENUM('low', 'medium', 'high')
);

drop table if exists staff;
CREATE TABLE staff (
    staff_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    password VARCHAR(45)
);

drop table if exists customer;
CREATe TABLE customer(
	customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45)
);

drop table if exists cart;
CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    staff_id INT,
    CONSTRAINT customer_id FOREIGN KEY (customer_id)
        REFERENCES romaniacs.customer (customer_id),
    CONSTRAINT staff_id FOREIGN KEY (staff_id)
        REFERENCES romaniacs.staff (staff_id)
);

drop table if exists games_by_card_id;
CREATE TABLE games_by_card_id (
    cart_id INT,
    game_id INT,
    game_qty INT,
    CONSTRAINT cart_id FOREIGN KEY (cart_id)
        REFERENCES romaniacs.cart (cart_id),
    CONSTRAINT game_id FOREIGN KEY (game_id)
        REFERENCES romaniacs.game (game_id)
);

