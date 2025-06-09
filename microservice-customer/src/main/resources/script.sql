CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE customers (
                           customer_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           encrypted_code VARCHAR(8) UNIQUE NOT NULL,
                           name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(80) NOT NULL,
                           document_type VARCHAR(12) NOT NULL,
                           document_number VARCHAR(12) UNIQUE NOT NULL,
                           creation_date DATE NOT NULL
);