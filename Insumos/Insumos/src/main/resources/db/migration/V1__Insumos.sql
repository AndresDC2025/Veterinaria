CREATE TABLE insumos (
    id integer NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    stock integer,
    fecha DATE,
    proveedor VARCHAR(50),
    PRIMARY KEY (id)
);