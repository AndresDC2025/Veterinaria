CREATE TABLE historial_medico (
    id integer NOT NULL AUTO_INCREMENT,
    diagnostico VARCHAR(255),
    tratamiento VARCHAR(255),
    notas VARCHAR(255),
    idMascota integer,
    /* fecha DATE, */
    PRIMARY KEY (id),

)