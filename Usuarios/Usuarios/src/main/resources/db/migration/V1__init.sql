CREATE TABLE usuarios (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    rut VARCHAR(12),
    email VARCHAR(200),
    telefono VARCHAR(15),
    direccion VARCHAR(100),
    id_mascota INTEGER,
    PRIMARY KEY (id)
);

INSERT INTO usuarios (nombre, rut, email, telefono, direccion, id_mascota)
VALUES ('Juan Perez', '12.345.678-9', 'juan@gmail.com', '123456789', 'Calle 123',1);