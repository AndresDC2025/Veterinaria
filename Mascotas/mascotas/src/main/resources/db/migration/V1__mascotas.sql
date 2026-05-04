CREATE TABLE mascotas (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    raza VARCHAR(50),
    edad INTeGER,
    PRIMARY KEY (id)
);

INSERT INTO mascotas (nombre, raza, edad) VALUES ('Firulais', 'Labrador', 5);
INSERT INTO mascotas (nombre, raza, edad) VALUES ('Mittens', 'Siamese', 3);