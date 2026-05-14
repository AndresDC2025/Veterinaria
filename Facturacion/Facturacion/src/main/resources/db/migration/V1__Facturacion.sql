CREATE TABLE facturacion (
    id INTEGER NOT NULL AUTO_INCREMENT,
    monto VARCHAR(255),
    metodoP VARCHAR(255),
    id_cita INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);