CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          encrypted_code VARCHAR(65) UNIQUE,
                          name VARCHAR(255) NOT NULL,
                          balance DECIMAL(19, 2) NOT NULL,
                          product_type VARCHAR(100) NOT NULL,
                          customer_code VARCHAR(64) UNIQUE,
                          creation_date DATE NOT NULL
);