CREATE TABLE mascotas (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    especie VARCHAR(50),
    raza VARCHAR(50),
    edad INTeGER,
    veterinario_id INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO mascotas (nombre, especie, raza, edad, veterinario_id) VALUES ('Firulais', 'Perro', 'Labrador', 5, 1);
INSERT INTO mascotas (nombre, especie, raza, edad, veterinario_id) VALUES ('Mittens', 'Perro', 'Siamese', 3, 2);