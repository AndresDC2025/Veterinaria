create table usuarios (
    id integer not null auto_increment,
    nombre varchar(50),
    rut varchar(12),
    email varchar(200),
    telefono varchar(15),
    direccion varchar(100),
    primary key (id)
);

INSERT INTO usuarios (nombre, rut, email, telefono, direccion) VALUES
('Juan Perez', '12.345.678-9', 'juanperez@gmail.com', '123456789', 'Calle Falsa 123');

