CREATE TABLE cita (
    id INTEGER NOT NULL AUTO_INCREMENT,
    fecha VARCHAR(20),
    hora VaRCHAR(20),
    motivo VARCHAR(500),
    usuario_id INTEGER NOT NULL,
    mascota_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);