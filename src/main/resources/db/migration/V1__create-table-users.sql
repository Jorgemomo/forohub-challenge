CREATE TABLE autores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    contraseña VARCHAR(300) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO autores (id, email, contraseña) VALUES
(1, 'jmorales@gmail.com', '=3:78AUxd?AK}3M/ev7#4cSd:EO1Se');