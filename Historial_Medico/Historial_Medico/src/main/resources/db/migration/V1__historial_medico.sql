CREATE TABLE historial_medico (

    id INTEGER NOT NULL AUTO_INCREMENT,
    diagnostico VARCHAR(255),
    tratamiento VARCHAR(255),
    descripcion VARCHAR(255),
    id_Mascota INTEGER NOT NULL,
    PRIMARY KEY (id)

);