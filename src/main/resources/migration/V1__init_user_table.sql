CREATE TABLE IF NOT EXISTS users(
    id INT PRIMARY KEY,
    email VARCHAR(64) NOT NULL UNIQUE,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    address VARCHAR(64),
    phone_number VARCHAR(64)
);