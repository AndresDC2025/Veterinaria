CREATE TABLE insumo (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    stock integer,
    fecha DATE,
    proveedor VARCHAR(50),
    /* FALTA LA CONEXION CON LAS DEMAS TABLAS */
    PRIMARY KEY (id)
);