DROP DATABASE IF EXISTS dockercon2017;
CREATE DATABASE IF NOT EXISTS dockercon2017;
USE `dockercon2017`;

CREATE TABLE `products_stock` (
  `p_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(50) not null unique ,
  `p_price` integer not null default 0,
  `p_quantity` integer not null default 0,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

INSERT INTO products_stock (p_id, p_name,p_price,p_quantity) VALUES(1, 'Paine', 10,10);
