CREATE TABLE tratamiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    dosis VARCHAR(100),
    duracion INT,
    id_historial BIGINT,
    inventario_id BIGINT
);