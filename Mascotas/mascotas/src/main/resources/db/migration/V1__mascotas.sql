CREATE TABLE mascotas (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    especie VARCHAR(50),
    raza VARCHAR(50),
    edad INT,
    id_veterinario INT,
    PRIMARY KEY (id)
);

INSERT INTO mascotas (nombre, especie, raza, edad, id_veterinario)
VALUES ('Firulais', 'Perro', 'Labrador', 5, 1);

INSERT INTO mascotas (nombre, especie, raza, edad, id_veterinario)
VALUES ('Mittens', 'Gato', 'Siamese', 3, 2);