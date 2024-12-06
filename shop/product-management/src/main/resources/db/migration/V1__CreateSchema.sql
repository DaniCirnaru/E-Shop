-- Create the database
CREATE DATABASE IF NOT EXISTS product_management_db;
USE product_management_db;

-- Create the categories table
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create the products table
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);
