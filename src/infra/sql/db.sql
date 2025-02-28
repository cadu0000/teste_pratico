CREATE DATABASE IF NOT EXISTS teste;

USE teste;

CREATE TABLE employee (
                          ID INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          birth_date DATE NOT NULL,
                          salary DECIMAL(10, 2) NOT NULL,
                          position VARCHAR(100) NOT NULL
);