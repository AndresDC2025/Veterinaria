CREATE TABLE historial_medico (

    id INTEGER NOT NULL AUTO_INCREMENT,
    diagnostico VARCHAR(255),
    tratamiento VARCHAR(255),
    notas VARCHAR(255),
    idMascota INTEGER,
    /* fecha DATE, */

    PRIMARY KEY (id)

);