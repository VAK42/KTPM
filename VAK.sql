DROP TABLE IF EXISTS export, import, product, report, "user" CASCADE;

CREATE TABLE "user" (
    userId SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO "user" (username, password, email, role) VALUES
('vak', '0000', 'vak@gmail.com', 'admin');

CREATE TABLE product (
    pdId SERIAL PRIMARY KEY,
    pdName VARCHAR(100) UNIQUE NOT NULL,
    pdPrice NUMERIC(12,2) NOT NULL,
    pdType VARCHAR(50) NOT NULL,
    pdInfo TEXT,
    pdQuantity INTEGER NOT NULL DEFAULT 0
);

INSERT INTO product (pdName, pdPrice, pdType, pdInfo, pdQuantity) VALUES
('NVIDIA GeForce RTX 5090', 1500.00, 'Electronics', 'NVIDIA RTX 5000 Series', 10),
('AMD Ryzen Threadripper Pro 7995WX', 10000.00, 'Electronics', 'AMD Ryzen Threadripper Pro Series', 5);

CREATE TABLE import (
    ipId SERIAL PRIMARY KEY,
    pdName VARCHAR(100) NOT NULL,
    pdPrice NUMERIC(12,2) NOT NULL,
    pdType VARCHAR(50) NOT NULL,
    pdQuantity INTEGER NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO import (pdName, pdPrice, pdType, pdQuantity, date) VALUES
('NVIDIA GeForce RTX 5090', 800.00, 'Electronics', 5, '2025-05-20 10:00:00'),
('AMD Ryzen Threadripper Pro 7995WX', 1200.00, 'Electronics', 2, '2025-05-22 11:00:00');

CREATE TABLE export (
    epId SERIAL PRIMARY KEY,
    pdId INTEGER NOT NULL,
    pdName VARCHAR(100) NOT NULL,
    pdType VARCHAR(50) NOT NULL,
    pdPrice NUMERIC(12,2) NOT NULL,
    pdQuantity INTEGER NOT NULL,
    pdTotalPrice NUMERIC(12,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_export_product FOREIGN KEY (pdId) REFERENCES product(pdId) ON DELETE RESTRICT
);

INSERT INTO export (pdId, pdName, pdType, pdPrice, pdQuantity, pdTotalPrice, date) VALUES
(1, 'NVIDIA GeForce RTX 5090', 'Electronics', 800.00, 2, 1600.00, '2025-04-26 14:00:00'),
(2, 'AMD Ryzen Threadripper Pro 7995WX', 'Electronics', 1200.00, 1, 1200.00, '2025-04-24 15:30:00');

CREATE TABLE report (
    reportId SERIAL PRIMARY KEY,
    userId INTEGER NOT NULL,
    rpName VARCHAR(100) NOT NULL,
    rpInfo TEXT,
    CONSTRAINT fk_report_user FOREIGN KEY (userId) REFERENCES "user"(userId) ON DELETE CASCADE
);

INSERT INTO report (userId, rpName, rpInfo) VALUES
(1, 'Button Glitch', 'Show Wrong Page!'),
(1, 'Checkout Error', 'Unable To Use Paypal!');

CREATE TABLE token (
    userid BIGINT NOT NULL REFERENCES "user"(userid) ON DELETE CASCADE,
    token VARCHAR(255) NOT NULL UNIQUE,
    date TIMESTAMP NOT NULL,
    PRIMARY KEY (userid, token)
);