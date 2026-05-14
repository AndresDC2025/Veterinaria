CREATE TABLE mascota (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    especie VARCHAR(50),
    raza VARCHAR(50),
    edad INTEGER,
    id_veterinario INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO mascotas (nombre, especie, raza, edad, id_veterinario) VALUES ('Firulais', 'Perro', 'Labrador', 5, 1);
INSERT INTO mascotas (nombre, especie, raza, edad, id_veterinario) VALUES ('Mittens', 'Perro', 'Siamese', 3, 2);