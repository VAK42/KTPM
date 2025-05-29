CREATE EXTENSION IF NOT EXISTS "pgcrypto";

DROP TABLE IF EXISTS export, import, product, report, category, token, "user" CASCADE;

CREATE TABLE "user" (
    userId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE token (
    userId UUID NOT NULL REFERENCES "user"(userId) ON DELETE CASCADE,
    token VARCHAR(255) NOT NULL UNIQUE,
    date TIMESTAMP NOT NULL,
    PRIMARY KEY (userId, token)
);

CREATE TABLE category (
    categoryId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE product (
    pdId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pdName VARCHAR(100) UNIQUE NOT NULL,
    pdPrice NUMERIC(12,2) NOT NULL,
    categoryId UUID NOT NULL REFERENCES category(categoryId) ON DELETE SET NULL,
    pdInfo TEXT NOT NULL,
    pdQuantity INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE import (
    ipId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pdId UUID NOT NULL REFERENCES product(pdId) ON DELETE RESTRICT,
    pdPrice NUMERIC(12,2) NOT NULL,
    pdQuantity INTEGER NOT NULL,
    userId UUID NOT NULL REFERENCES "user"(userId) ON DELETE SET NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE export (
    epId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pdId UUID NOT NULL REFERENCES product(pdId) ON DELETE RESTRICT,
    pdPrice NUMERIC(12,2) NOT NULL,
    pdQuantity INTEGER NOT NULL,
    pdTotalPrice NUMERIC(12,2) GENERATED ALWAYS AS (pdPrice * pdQuantity) STORED,
    userId UUID NOT NULL REFERENCES "user"(userId) ON DELETE SET NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE report (
    reportId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    userId UUID NOT NULL REFERENCES "user"(userId) ON DELETE CASCADE,
    rpName VARCHAR(100) NOT NULL,
    rpInfo TEXT NOT NULL
);

INSERT INTO "user" (username, password, email, role)
VALUES 
('admin', '1412', '23017215@st.phenikaa-uni.edu.vn', 'admin');

INSERT INTO category (name)
VALUES 
('Mobile'),
('Tablet'),
('PC'),
('Laptop');

INSERT INTO report (userId, rpName, rpInfo)
VALUES 
((SELECT userId FROM "user" WHERE username = 'admin'), 'Monthly Summary', 'Sales & Inventory Summary For The Month'),
((SELECT userId FROM "user" WHERE username = 'admin'), 'Low Stock Alert', 'Several Products Have Reached Low Stock Levels'),
((SELECT userId FROM "user" WHERE username = 'admin'), 'Annual Performance', 'Performance Overview Of All Product Categories'),
((SELECT userId FROM "user" WHERE username = 'admin'), 'Customer Feedback Report', 'Compiled Customer Reviews & Suggestions'),
((SELECT userId FROM "user" WHERE username = 'admin'), 'Supplier Issues', 'Delay Reports & Quality Concerns From Key Suppliers');

INSERT INTO product (pdName, pdPrice, categoryId, pdInfo, pdQuantity)
VALUES
('Samsung Galaxy S24', 899.99, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Samsung Flagship Smartphone', 30),
('iPhone 15 Pro', 1199.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Apple Premium Smartphone', 25),
('Google Pixel 8 Pro', 999.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Google AI-powered Smartphone', 20),
('OnePlus 12', 799.99, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Flagship Killer Phone', 22),
('ASUS ROG Phone 7', 1099.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Gaming Phone With AMOLED Screen', 18),
('Xiaomi 14 Ultra', 799.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'High-end Chinese Smartphone', 19),
('Sony Xperia 1 V', 999.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Pro Photography Smartphone', 15),
('Motorola Edge 40 Pro', 699.00, (SELECT categoryId FROM category WHERE name = 'Mobile'), 'Affordable Flagship Device', 20),
('iPad Pro M2', 1099.99, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Apple M2-powered Tablet', 20),
('Samsung Galaxy Tab S9', 799.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Samsung AMOLED Tablet', 20),
('Lenovo Tab Extreme', 649.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Large Android Productivity Tablet', 18),
('Xiaomi Pad 6', 499.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Budget High-res Tablet', 25),
('Amazon Fire Max 11', 229.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Affordable Large-screen Tablet', 15),
('Huawei MatePad Pro', 599.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Premium HarmonyOS Tablet', 16),
('ASUS ZenPad 10', 399.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Mid-range Productivity Tablet', 17),
('TCL NXTPAPER 10s', 329.00, (SELECT categoryId FROM category WHERE name = 'Tablet'), 'Eye-care Tablet With Paper-like Screen', 12),
('Intel Core i9-13900K', 599.00, (SELECT categoryId FROM category WHERE name = 'PC'), '13th Gen Intel CPU', 40),
('AMD Ryzen 9 7950X', 649.00, (SELECT categoryId FROM category WHERE name = 'PC'), 'High-end AMD CPU', 35),
('NVIDIA RTX 4090', 1599.00, (SELECT categoryId FROM category WHERE name = 'PC'), 'NVIDIA Flagship GPU', 10),
('AMD Radeon RX 7900 XTX', 999.00, (SELECT categoryId FROM category WHERE name = 'PC'), 'AMD Flagship GPU', 12),
('ASUS TUF B650M', 189.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'AM5 Motherboard', 30),
('Gigabyte Z790 AORUS', 299.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'Z790 Motherboard', 20),
('MSI MAG B550M', 129.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'Budget B550 Motherboard', 25),
('Intel Core i7-13700K', 429.99, (SELECT categoryId FROM category WHERE name = 'PC'), '13th Gen i7 CPU', 38),
('Corsair Vengeance 32GB DDR5', 149.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'DDR5 RAM Kit', 28),
('Samsung 990 Pro 1TB SSD', 169.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'High-speed Gen4 SSD', 30),
('Cooler Master Hyper 212', 49.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'Air CPU Cooler', 32),
('Be Quiet! Pure Power 12 750W', 119.99, (SELECT categoryId FROM category WHERE name = 'PC'), 'Efficient PSU', 22),
('ASUS ROG Zephyrus G14', 1599.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Gaming Laptop RTX 4070', 12),
('Gigabyte Aero 16', 1799.99, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'OLED Creative Laptop', 8),
('Dell XPS 13 Plus', 1399.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Intel Ultrabook', 15),
('Apple MacBook Air M3', 1299.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'M3 Chip Ultrabook', 18),
('HP Spectre x360', 1499.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Premium Convertible Laptop', 10),
('Lenovo Legion 5 Pro', 1399.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Gaming Laptop RTX 4060', 14),
('MSI Katana GF66', 1199.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Budget Gaming Laptop', 20),
('ASUS VivoBook Pro 16X', 1399.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Creator Laptop', 15),
('Acer Swift X 14', 1299.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Ultrabook With Dedicated GPU', 12),
('Microsoft Surface Laptop 5', 1299.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Premium Windows Laptop', 10),
('Razer Blade 14', 2199.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'High-end Gaming Laptop', 6),
('Framework Laptop 13', 999.00, (SELECT categoryId FROM category WHERE name = 'Laptop'), 'Repairable Modular Laptop', 9);

INSERT INTO import (pdId, pdPrice, pdQuantity, userId)
SELECT pdId, pdPrice, pdQuantity, (SELECT userId FROM "user" WHERE username = 'admin')
FROM product;

INSERT INTO export (pdId, pdPrice, pdQuantity, userId)
VALUES
((SELECT pdId FROM product WHERE pdName = 'Samsung Galaxy S24'), 899.99, 3, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'iPhone 15 Pro'), 1199.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Google Pixel 8 Pro'), 999.00, 1, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'ASUS ROG Phone 7'), 1099.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'iPad Pro M2'), 1099.99, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Samsung Galaxy Tab S9'), 799.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Lenovo Tab Extreme'), 649.00, 1, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Intel Core i9-13900K'), 599.00, 4, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'AMD Ryzen 9 7950X'), 649.00, 3, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'NVIDIA RTX 4090'), 1599.00, 1, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'AMD Radeon RX 7900 XTX'), 999.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'ASUS ROG Zephyrus G14'), 1599.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Dell XPS 13 Plus'), 1399.00, 1, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Apple MacBook Air M3'), 1299.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Lenovo Legion 5 Pro'), 1399.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Framework Laptop 13'), 999.00, 1, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Microsoft Surface Laptop 5'), 1299.00, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'MSI MAG B550M'), 129.99, 3, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Corsair Vengeance 32GB DDR5'), 149.99, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Samsung 990 Pro 1TB SSD'), 169.99, 3, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Cooler Master Hyper 212'), 49.99, 2, (SELECT userId FROM "user" WHERE username = 'admin')),
((SELECT pdId FROM product WHERE pdName = 'Be Quiet! Pure Power 12 750W'), 119.99, 2, (SELECT userId FROM "user" WHERE username = 'admin'));